# Arquitectura de ClassHub

## Visión general

ClassHub es una plataforma educativa con arquitectura **monorepo**. El sistema consta de:

- **Backend**: una API de Spring Boot 4 (Java 21) con arquitectura hexagonal/limpia por dominio. Expone tanto una API **REST** como una **GraphQL** parcial. Seguridad mediante **JWT** sin estado. Usa **PostgreSQL** con la extensión **pgvector** para el almacenamiento vectorial del asistente de IA.
- **Frontend**: aplicación **Next.js 16** (App Router, React 19, TypeScript) que consume el backend vía REST y GraphQL usando cookies `httpOnly` para la sesión.
- **Infraestructura**: Docker Compose que orquesta el backend y la base de datos `pgvector`.

```
                 +-----------------------+
   Browser  -->  |      Frontend        |   (Next.js 16, puerto 3000)
                 |  App Router + Proxy  |
                 +-----------+----------+
                             |  REST + GraphQL (API_URL, puerto 8080)
                             v
                 +-----------------------+
                 |      Backend         |   (Spring Boot 4, puerto 8080)
                 |  REST + GraphQL + JWT|
                 +-----------+----------+
                             |
                             v
                 +-----------------------+
                 |  PostgreSQL + pgvector|  (puerto 5432)
                 +-----------------------+
```

## Componentes

### Backend (`apps/backend`)

- **Spring Boot 4.1.0 / Java 21**, arquitectura hexagonal por dominio (`application` / `domain` / `infrastructure`).
- **Contratos**: API REST (`/auth`, `/courses`, `/topics`, `/topic/{id}/resources`, `/topic/{id}/chat`, `/users`, `/profiles`) y GraphQL (`/graphql` con `courses` y `dashboard`).
- **Autenticación**: JWT sin estado (access + refresh), refresh almacenado en DB hasheado (SHA‑256) y revocable; BCrypt para contraseñas.
- **IA / RAG**: los recursos subidos por el profesor se procesan (Tika → división en fragmentos → embeddings con Gemini) y se guardan en **PGVector**. Cuando un estudiante pregunta, se hace una búsqueda vectorial filtrada por tema/recurso y se genera la respuesta con **Gemini (Spring AI)**.
- **Correo**: JavaMailSender con SMTP de Gmail y plantillas Thymeleaf para la activación de cuenta.

### Frontend (`apps/frontend`)

- **Next.js 16 / React 19 / TypeScript**, `bun` como gestor de paquetes.
- **Estilos**: Tailwind CSS 4 + componentes **shadcn** sobre **Base UI**.
- **Datos**: dos clientes de servidor (`src/lib/api.ts` REST y `src/lib/graphql.ts` GraphQL), server actions y caché de Next (`next/cache`).
- **Sesión**: JWT en cookies `httpOnly` (`access_token`, `refresh_token`), refresco automático y protección de rutas vía `src/proxy.ts` (Middleware de Next 16).
- **Archivos**: subida a **Cloudinary** desde un Route Handler (`/api/upload`).

### Infraestructura (`infrastructure/docker`)

`compose.yml` define dos servicios:

| Servicio | Imagen | Puerto | Volumen |
|---|---|---|---|
| `backend` | construida desde `apps/backend/Dockerfile` | 8080 | — |
| `db` | `pgvector/pgvector:pg15` | 5432 | `class-hub-data` (named volume) |

El backend lee el entorno de `apps/backend/.env` y depende de que `db` esté arriba.

## Flujo de autenticación y activación

1. **Registro** → `POST /auth/register` crea un usuario **desactivado** (rol STUDENT) y publica un evento de correo de activación.
2. El **listener asíncrono** envía un enlace con un **token de activación de 15 minutos** (hasheado en DB).
3. El usuario hace clic → `GET /auth/activate?token=` → valida el token, **activa** la cuenta y emite tokens de sesión.
4. **Login** → `POST /auth/login` → verifica credenciales BCrypt y emite un par `access`/`refresh`.
5. **Refresh** → `POST /auth/refresh` rota el refresh token (revoca el anterior) y emite un nuevo par.

> **Limitación de despliegue:** el envío de correos usa Gmail SMTP. En **Render (capa gratuita) el SMTP está bloqueado**, por lo que **no se puede activar una cuenta** en el despliegue de producción. Ver README y `docs/backend.md`.

## Pipeline RAG

```
Profesor sube recurso (URL) ──> TopicResourceController
        │  (asíncrono)
        v
TopicResourceProcessor: descarga → Tika (parseo) → TokenTextSplitter (chunks ~800)
        v
Embeddings (Gemini, 1536 dim) ──> PGVector (tabla ai_docs, HNSW + cosine)
        │
Pregunta del estudiante ──> MakeQueryService
        │  búsqueda vectorial por tema/recurso (topK 6)
        v
Gemini (chat) responde con contexto ──> se guarda en chat_messages
```

## Modelo de datos

Tablas principales (gestionadas por Hibernate con `ddl-auto`):

- `users` — email, contraseña encriptada, rol (TEACHER/STUDENT/ADMIN), habilitado, último login.
- `profiles` — nombre, apellido, avatar (`1:1` con `users`).
- `courses` — nombre, descripción, slug, habilitado, docente (`N:1` con `users`).
- `topics` — título (`N:1` con `courses`, borrado en cascada).
- `topic_resources` — archivo/URL, tipo (PDF/DOCX/PPTX), estado de procesamiento.
- `chat_messages` — pregunta/respuesta (`N:1` con `topics` y `users`).
- `refresh_tokens` — token hasheado, revocado, expiración.
- `activation_tokens` — token de activación, usado, expiración.
- `ai_docs` — tabla vectorial de pgvector para los fragmentos indexados.

## Variables de entorno (nombres de clave)

**Backend** (`apps/backend/.env` para Docker, `.env.local` para local): `POSTGRES_*`, `SPRING_DATASOURCE_*`, `DDL_AUTO`, `SHOW_SQL`, `JWT_ACCESS`, `JWT_REFRESH`, `JWT_EXP_ACCESS`, `JWT_EXP_REFRESH`, `SPRING_MAIL_USERNAME`, `SPRING_MAIL_PASSWORD`, `GEMINI_API_KEY`, `GEMINI_CHAT_MODEL`, `GEMINI_EMBEDDING_MODEL`, `FRONTEND_URL`.

**Frontend** (`apps/frontend/.env`): `API_URL`, `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET`.

> No existen `.env.example` en el repositorio. Los valores de estas claves se documentan en `docs/backend.md` y `docs/frontend.md`.

## Estado del proyecto

Existen funcionalidades completas y otras **pendientes o incompletas**:

- ✅ Registro/login/activación (el envío de correos falla en Render por SMTP bloqueado).
- ✅ Gestión de cursos, temas y recursos.
- ✅ Chat con IA (RAG) sobre los recursos del curso.
- ✅ Gestión de usuarios (roles y estados).
- ❌ **Inicio de sesión con Google** (solo UI decorativa, sin flujo OAuth).
- ❌ Varios dashboards y vistas con datos mock (estudiantes, detalle de usuario, edición de perfil).
- ❌ Logout no implementado.
- ❌ `.env.example` no existe.

Para la lista completa de pendientes y fixes, ver el **README** raíz.
