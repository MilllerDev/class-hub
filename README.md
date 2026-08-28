# ClassHub

Plataforma educativa de gestión de cursos con asistente de IA basado en RAG. Permite a los profesores crear cursos y temas, subir recursos (PDF, DOCX, PPTX), y responder preguntas de los estudiantes sobre el contenido mediante un chatbot con IA.

## Stack tecnológico

Este es un **monorepo** dividido en tres áreas:

| Carpeta | Tecnología | Descripción |
|---|---|---|
| `apps/backend` | Java 21, Spring Boot 4.1.0 | API REST + GraphQL, seguridad JWT, PostgreSQL + pgvector, IA (Google Gemini / Spring AI) |
| `apps/frontend` | Next.js 16, React 19, TypeScript | App Router, Tailwind CSS 4, shadcn/Base UI, `bun` como gestor de paquetes |
| `infrastructure/docker` | Docker Compose | Orquestación de backend y base de datos pgvector |

### Roles

- **STUDENT** — ve cursos habilitados, temas y chatea con la IA sobre el contenido del curso.
- **TEACHER** — crea/edita/elimina temas y recursos de sus propios cursos.
- **ADMIN** — gestiona usuarios (roles y estados), cursos y el dashboard.

### Características principales

- Registro, login y **activación de cuenta por correo** (JWT + tokens temporales).
- CRUD de cursos, temas y recursos por curso.
- **Chat con IA**: el profe sube recursos, se indexan en un vector store (PGVector) y los estudiantes hacen preguntas que se responden con Gemini usando la información de los recursos (RAG).
- Gestión de usuarios (cambio de rol, activar/desactivar cuentas).
- Exposición de datos por REST y parcialmente por GraphQL.

## Cómo ejecutar local

### Opción A — Docker Compose (backend + base de datos)

```bash
# Desde la raíz del proyecto
docker compose -f infrastructure/docker/compose.yml up --build
```

- Backend disponible en `http://localhost:8080`
- PostgreSQL (con extensión `pgvector`) en `localhost:5432`

> Requiere tener las variables de entorno definidas en `apps/backend/.env` (ver `docs/backend.md`).

### Opción B — Ejecución local separada

1. **Base de datos**: levantar PostgreSQL con la extensión `pgvector` (p. ej. con Docker) y cargar las variables de `apps/backend/.env.local` (apuntan a `localhost`).
2. **Backend** (Spring Boot, puerto 8080):

   ```bash
   cd apps/backend
   ./mvnw spring-boot:run
   ```

3. **Frontend** (Next.js, puerto 3000):

   ```bash
   cd apps/frontend
   bun install
   bun dev
   ```

   Abrir `http://localhost:3000`. El frontend se conecta al backend mediante la variable `API_URL` (ver `docs/frontend.md`).

> Instrucciones detalladas por aplicación: [`docs/backend.md`](docs/backend.md) y [`docs/frontend.md`](docs/frontend.md).

## Usuarios de prueba

| Rol | Email | Contraseña |
|---|---|---|
| **STUDENT** | `alvarezsalasangelica8@gmail.com` | `miller_1234` |
| **TEACHER** | `docente@gmail.com` | `miller_1234` |
| **ADMIN** | `admin@gmail.com` | `miller_1234` |

> Todos los usuarios comparten la misma contraseña `miller_1234`. Se puede acceder  con estos usuarios en: class-hub-seven.vercel.app porque las cuentas ya estén activadas (recuerda que la activación por correo está bloqueada en Render, ver sección siguiente).

## Puntos pendientes / a implementar

### Inicio de sesión con Google (no implementado)

La interfaz muestra el botón **"Continuar con Google"** en el login y el registro, pero **es puramente decorativo**:

- No tiene manejador de click, ni redirección OAuth, ni endpoint.
- No hay variables de entorno de OAuth (`GOOGLE_CLIENT_ID`, `GOOGLE_CLIENT_SECRET`), ni en backend ni en frontend.

El único uso de "Google" en el proyecto es `Google GenAI` (Gemini) para la IA, no OAuth.

### Activación de cuenta — SMTP bloqueado en Render (capa gratuita)

El flujo de activación **existe y está implementado**: al registrarse se envía un correo con un enlace de activación de 15 minutos que activa la cuenta.

**Importante:** el envío de correos usa **Gmail SMTP** (`smtp.gmail.com`) desde el backend. En **Render (capa gratuita) el SMTP está bloqueado**, por lo que **los correos de activación no llegan a enviarse en el despliegue**, y por tanto **no se puede activar una cuenta** en producción. Esto impide completar el registro en el entorno desplegado.

Implicaciones:
- En producción la activación no funciona hasta configurar un proveedor SMTP no bloqueado (p. ej. SendGrid, Resend, etc.).
- Para desarrollo/pruebas locales el SMTP sí puede funcionar si se configuran credenciales de Gmail válidas.

### Fixes pendientes

**Backend**
- Sin `.env.example` (las claves solo están documentadas aquí; los `.env` reales tienen secretos en texto plano).
- El envío de correos usa **SMTP de Gmail** y las credenciales se configuran con `SPRING_MAIL_USERNAME` y `SPRING_MAIL_PASSWORD` (el emisor está **hardcodeado** en `MailSenderService`).
- Código legado: `RagController`/`RagService` (raíz del paquete) están superados por el módulo `ragquery` + `TopicResourceProcessor`. Posible código redundante/duplicado.
- Código muerto: `ProfileRequest` + `ProfileMapper.toDomain(...)` (nunca consumidos) y `GraphQLExceptionResolver.mapErrorType()` (nunca llamado).
- `AddTopicResourceService` lanza un `RuntimeException` crudo ("No tienes permisos sobre este tema"); `PgVectorSearchAdapter` lanza `IllegalArgumentException` — deberían ser excepciones de dominio tipadas.
- Inconsistencia de autorización en el chat: `POST /chat/messages` no valida rol, pero `GET`/`DELETE` sí exigen `STUDENT`.
- Mensajes de validación inconsistentes (p. ej. `lastName` dice "5 a 20 caracteres" pero el límite real es 50; password dice "al menos 8" pero el mínimo es 6).
- `CourseRequest.useAi` y `PartialCourseRequest.useAi` son obligatorios en cada petición, incluso en actualizaciones parciales.

**Frontend**
- **No existe funcionalidad de logout** (no hay botón ni acción; solo expiración de sesión / vaciado de cookies en 401).
- Dashboard de estudiante (`/student`) y varias estadísticas del dashboard de admin están **hardcodeadas con datos mock**.
- Vistas mock sin conectar: `/admin/users/[id]` (detalle de usuario) y el formulario de edición de perfil (guarda solo en local).
- `removeCourseTeacher` es un *stub* (solo hace `console.log`, no llama al backend).
- El switch "Generar descripción con IA" (`useAi`) no dispara una llamada real a la IA — solo deshabilita el textarea.
- `/activate-account` redirige a `/` cuando hay `email` y `token` a la vez (posible bug de guard).
- `next-themes` instalado pero sin `ThemeProvider`; GoogleIcon duplicado en `register-form`; query de GraphQL de profiles sin usar; sin tests.

## Estructura del repositorio

```
class-hub/
├── apps/
│   ├── backend/          # Spring Boot (REST + GraphQL + JWT + RAG)
│   └── frontend/         # Next.js 16 (App Router)
├── docs/                 # Documentación
│   ├── arquitecture.md
│   ├── backend.md
│   └── frontend.md
└── infrastructure/
    └── docker/           # Docker Compose (backend + db pgvector)
```

## Documentación

- [`docs/arquitecture.md`](docs/arquitecture.md) — visión general de la arquitectura.
- [`docs/backend.md`](docs/backend.md) — backend en detalle (endpoints, modelos, entorno, ejecución).
- [`docs/frontend.md`](docs/frontend.md) — frontend en detalle (rutas, sesión, entorno, ejecución).
