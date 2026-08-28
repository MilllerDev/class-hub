# Backend — Documentación detallada

Carpeta: `apps/backend`

API de **Spring Boot 4.1.0** (Java 21) para ClassHub. Expone una API **REST** y una **GraphQL** parcial, usa **JWT** sin estado, **PostgreSQL + pgvector** y un asistente de IA basado en **RAG** (Google Gemini / Spring AI).

## Stack y dependencias

| Área | Tecnología |
|---|---|
| Framework | Spring Boot **4.1.0** |
| Lenguaje | Java **21** |
| Mapeo DTO/entidades | **MapStruct** 1.6.3 |
| Tokens | **JJWT** 0.13.0 (access/refresh) |
| Persistencia | Spring Data JPA + **PostgreSQL** + **pgvector** |
| IA | **Spring AI 2.0.0** (Gemini GenAI, embeddings, Tika, PGVector store) |
| Correo | Spring Mail (Gmail SMTP) + **Thymeleaf** |
| GraphQL | Spring GraphQL |
| Build | Maven 3.9.x (`./mvnw`, `mvnw.cmd`) |

## Estructura de paquetes

Arquitectura **hexagonal/limpia**: cada dominio divide `application` (servicios + puertos), `domain` (modelos, excepciones, eventos) e `infrastructure` (rest, persistencia, mappers, graphql, ai). Ubicación base: `src/main/java/com/bailon/backend`.

| Paquete | Función |
|---|---|
| `auth` | Registro, login, activación, reenvío de activación. |
| `chat` | Chat por tema (pregunta/respuesta con IA). |
| `config` | Seguridad, JWT, asíncrono, mail, GraphQL, manejo global de errores. |
| `courses` | CRUD de cursos, con descripción opcional generada por IA. |
| `dashboard` | Datos del dashboard de administración (GraphQL). |
| `profile` | Perfiles de usuario. |
| `ragquery` | Pipeline RAG: búsqueda vectorial + respuesta de Gemini. |
| `token` | Emisión/validación de JWT, refresh y tokens de activación. |
| `topicresources` | Recursos por tema (archivos/URLs) e ingesta vectorial. |
| `topics` | CRUD de temas. |
| `users` | Gestión de usuarios y principal de seguridad. |

> Nota: en el paquete raíz existen `RagController` y `RagService` como **código legado**, superados por `ragquery` + `TopicResourceProcessor`.

## Endpoints REST

### Autenticación (`/auth`) — públicas

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/auth/register` | Registra un nuevo estudiante (crea usuario desactivado + perfil) y dispara el correo de activación. |
| POST | `/auth/login` | Login, devuelve `Auth` (access + refresh + rol). |
| POST | `/auth/refresh` | Rota el refresh token y emite un nuevo par. |
| GET | `/auth/activate?token=` | Activa la cuenta con el token de 15 min y emite sesión. |
| POST | `/auth/resend-activation` | Reenvía el correo de activación a un usuario pendiente. |

### Cursos (`/courses`)

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| GET | `/courses/{slug}` | autenticado | Curso por slug (acceso según rol; no-admin solo habilitados). |
| POST | `/courses` | ADMIN | Crea curso (descripción con IA opcional). |
| PATCH | `/courses/{id}` | ADMIN | Actualiza curso. |
| DELETE | `/courses/{id}` | ADMIN | Elimina curso y purga sus vectores. |

### Temas (`/topics`)

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| GET | `/topics/course/{slug}?sort=` | autenticado | Lista temas de un curso. |
| GET | `/topics/{id}` | autenticado | Un tema. |
| POST | `/topics` | TEACHER | Crea tema (debe ser dueño del curso). |
| PATCH | `/topics/{id}` | TEACHER | Actualiza tema. |
| DELETE | `/topics/{id}` | TEACHER | Elimina tema y purga sus vectores. |

### Recursos (`/topic/{topicId}/resources`)

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| POST | `/topic/{id}/resources` | TEACHER | Añade recurso (URL) e inicia el procesamiento vectorial en segundo plano. |
| GET | `/topic/{id}/resources` | autenticado | Lista recursos de un tema (según rol). |
| GET | `/topic/{id}/resources/{rid}` | autenticado | Un recurso. |
| DELETE | `/topic/{id}/resources/{rid}` | TEACHER | Elimina recurso y sus vectores. |
| POST | `/topic/{id}/resources/{rid}/reprocess` | TEACHER | Reprocesa un recurso con estado `FAILED`. |

### Chat (`/topic/{topicId}/chat`)

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| POST | `/topic/{id}/chat/messages` | autenticado | Hace una pregunta, obtiene respuesta RAG y guarda el mensaje. |
| GET | `/topic/{id}/chat/messages` | STUDENT | Historial del chat. |
| DELETE | `/topic/{id}/chat/messages/{mid}` | STUDENT | Elimina un mensaje propio. |

> Nota de diseño: el `POST` no valida rol, mientras que `GET`/`DELETE` exigen rol `STUDENT` (inconsistencia documentada como fix pendiente).

### Usuarios (`/users`)

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| PATCH | `/users/{id}/role` | ADMIN | Cambia el rol de un usuario. |
| PATCH | `/users/{id}/status` | ADMIN | Habilita/deshabilita cuenta (revoca tokens al deshabilitar). |

### Perfiles (`/profiles`)

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| GET | `/profiles` | autenticado | Perfil propio. |
| GET | `/profiles/list?q=&role=&page=&size=` | ADMIN | Lista paginada/búsqueda de usuarios. |
| GET | `/profiles/role?role=` | ADMIN | Perfiles por rol. |

### Legado (`/rag`) — código viejo

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/rag/upload` | Sube documento → Tika → split → vector store. |
| GET | `/rag/ask?query=` | Pregunta RAG simple. |

## GraphQL

Punto de entrada único `/graphql`. Schema principal (`src/main/resources/graphql`):

- **Query `courses`** — listado de cursos según rol (`Course.profile` resuelto con DataLoader).
- **Query `dashboard`** — métricas del dashboard (`@PreAuthorize ADMIN`).

La mayor parte de la funcionalidad es REST; GraphQL cubre solo cursos y dashboard.

## Modelo de datos y repositorios

Patrón **puerto/adaptador**: cada dominio define un puerto (interface `…Repository`) en `application` y lo implementa con un adaptador JPA en `infrastructure`.

| Entidad | Tabla | Puerto | Adapter JPA |
|---|---|---|---|
| `UserEntity` | `users` | `UserRepository` | `UserRepositoryAdapter` |
| `CourseEntity` | `courses` | `CourseRepository` | `CourseRepositoryAdapter` |
| `TopicEntity` | `topics` | `TopicRepository` | `TopicRepositoryAdapter` |
| `ChatMessageEntity` | `chat_messages` | `ChatMessageRepository` | `ChatMessageRepositoryAdapter` |
| `ProfileEntity` | `profiles` | `ProfileRepository` | `ProfileRepositoryAdapter` |
| `TopicResourceEntity` | `topic_resources` | `TopicResourceRepository` | `TopicResourceRepositoryAdapter` |
| `TokenEntity` | `refresh_tokens` | `TokenRepositoryPort` | `TokenRepositoryAdapter` |
| `ActivationTokenEntity` | `activation_tokens` | `ActivationTokenPort` | `ActivationTokenAdapter` |

Esquema gestionado por Hibernate con `ddl-auto` (default `update`). Claves principales:

- `users`: email (único), contraseña BCrypt, rol (`TEACHER`/`STUDENT`/`ADMIN`), habilitado, último login.
- `courses`: nombre, descripción, slug (único), habilitado, docente (`N:1` users).
- `topics`: título, `N:1` courses (cascada).
- `topic_resources`: fileName, fileUrl, tipo (`PDF`/`DOCX`/`PPTX`), estado (`PENDING`/`PROCESSING`/`COMPLETED`/`FAILED`), `N:1` topic (cascada).
- `chat_messages`: pregunta/respuesta, `N:1` topic/user.
- `refresh_tokens` / `activation_tokens`: tokens hasheados (SHA‑256), revocado/usado, expiración.
- `ai_docs`: tabla vectorial pgvector (1536 dims, HNSW + cosine) para los fragmentos indexados.

## Seguridad y autenticación

- **JWT sin estado**: acceso (default 3600 s) y refresh (86400 s). Sesiones deshabilitadas (`STATELESS`), CSRF off.
- Filtro `JwtAuthenticationFilter` valida el header `Authorization: Bearer`.
- Refresh tokens y tokens de activación almacenados **hasheados** en DB y **revocables**.
- Contraseñas con **BCrypt**.
- Clases clave: `SecurityConfiguration`, `JwtAuthenticationFilter`, `JwtService`, `TokenIssuerService`, `RefreshTokenService`, `JwtProperties`, `CustomUserDetailsService`, `JwtAuthenticationEntryPoint`.

## Flujo de activación de cuenta

1. `POST /auth/register` → crea usuario **desactivado** (rol STUDENT) y publica `ActivationEmailRequestedEvent`.
2. `ActivationEmailListener` (asíncrono + `AFTER_COMMIT`) construye `{FRONTEND_URL}/activate-account?token=…` y lo envía (token válido **15 min**).
3. `GET /auth/activate?token=` → valida token → **activa** cuenta → registra login → emite sesión.
4. `POST /auth/resend-activation` → reemite token para usuarios pendientes.

### ⚠️ SMTP bloqueado en Render (capa gratuita)

El envío usa **Gmail SMTP** (`smtp.gmail.com:587`, STARTTLS) mediante `MailSenderService`. En **Render (capa gratuita) el SMTP está bloqueado**, por lo que **los correos de activación no se envían en el despliegue** y **no es posible activar una cuenta** en producción.

Consecuencias y recomendaciones:
- Para producción, usar un proveedor SMTP no bloqueado y configurar las credenciales del correo con `SPRING_MAIL_USERNAME` y `SPRING_MAIL_PASSWORD` (actualmente el emisor está **hardcodeado** en `MailSenderService`, por lo que conviene externalizarlo a variables de entorno).
- Para desarrollo local, configurar credenciales de Gmail válidas.

## Pipeline RAG (IA)

```
URL del recurso → TopicResourceProcessor (asíncrono):
   descarga → Tika (parseo) → TokenTextSplitter (chunks ~800) → embeddings (Gemini) → PGVector
Pregunta del estudiante → MakeQueryService:
   búsqueda vectorial filtrada por tema/recurso (topK 6) → Gemini (chat) → respuesta guardada en chat_messages
```

Adaptadores: `GeminiChatAdapter` (port `LlmChatPort`), `PgVectorSearchAdapter` (port `ResourceVectorSearchPort`), `CourseAiService` para generación de descripciones con IA.

## Variables de entorno (`apps/backend/.env`)

Crear un `.env` Las claves (sin valores) son:

| Clave | Uso |
|---|---|
| `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD` | Credenciales de la base. |
| `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` | Conexión JDBC (Docker: `db` / local: `localhost`). |
| `DDL_AUTO` | Esquema Hibernate (default `update`). |
| `SHOW_SQL` | Mostrar SQL. |
| `JWT_ACCESS`, `JWT_REFRESH` | Secretos de firma. |
| `JWT_EXP_ACCESS`, `JWT_EXP_REFRESH` | Expiración (segundos). |
| `SPRING_MAIL_USERNAME`, `SPRING_MAIL_PASSWORD` | Credenciales del SMTP de Gmail para el envío de correos. |
| `GEMINI_API_KEY`, `GEMINI_CHAT_MODEL`, `GEMINI_EMBEDDING_MODEL` | Gemini/IA. |
| `FRONTEND_URL` | URL base del frontend (para enlaces de activación). |


## Ejecución local

### Requisitos previos

- JDK 21
- PostgreSQL con la extensión **pgvector** corriendo (p. ej. con Docker)
- Variables de entorno cargadas (usar `apps/backend/.env.local`, que apunta a `localhost`)

### Levantar la base de datos (opcional, con Docker)

```bash
docker compose -f infrastructure/docker/compose.yml up db
```

### Compilar y ejecutar

```bash
cd apps/backend
./mvnw clean package -DskipTests   # compilar
./mvnw spring-boot:run             # o ejecutar directamente
```

En Windows: `./mvnw.cmd` en lugar de `./mvnw`.

El backend queda en `http://localhost:8080` (puerto por defecto, `EXPOSE 8080` en el Dockerfile).

### Tests

Solo existe el test de arranque por defecto (`BackendApplicationTests`).

## Dockerfile

Multi-stage: `maven:3.9.12-eclipse-temurin-21` compila, y `eclipse-temurin:21-jre-jammy` ejecuta `java -jar app.jar`. Expone el puerto **8080**.

## Pendientes y fixes (backend)

- ❌ **Sesión con Google** no implementada (no hay login social; solo Gemini como IA).
- ❌ El envío usa SMTP de Gmail con emisor no es posible porque render bloquea los puertos; las credenciales deben configurarse vía `SPRING_MAIL_USERNAME`/`SPRING_MAIL_PASSWORD` (**bloqueado en Render** → la activación de cuenta no funciona en producción).
- 🧹 Código legado `RagController`/`RagService` superado por `ragquery`.
- 🧹 Código muerto: `ProfileRequest` + `ProfileMapper.toDomain(...)` y `GraphQLExceptionResolver.mapErrorType()`.
- 🧹 `AddTopicResourceService` lanza `RuntimeException` crudo; `PgVectorSearchAdapter` lanza `IllegalArgumentException` (usar excepciones de dominio).
- 🧹 Inconsistencia de autorización en chat: `POST` sin rol vs `GET/DELETE` con `STUDENT`.
- 🧹 Mensajes de validación inconsistentes (p. ej. `lastName` "5 a 20" vs límite 50; password "al menos 8" vs mínimo 6).
- 🧹 `CourseRequest.useAi` / `PartialCourseRequest.useAi` obligatorios incluso en actualizaciones parciales.
