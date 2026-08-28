# Frontend — Documentación detallada

Carpeta: `apps/frontend`

Aplicación **Next.js 16** (App Router) en **React 19 / TypeScript**, con **Tailwind CSS 4** y componentes **shadcn** sobre **Base UI**. Gestiona la sesión del usuario con cookies `httpOnly` y consume el backend (Spring Boot) vía **REST** y **GraphQL**. Usa **`bun`** como gestor de paquetes.

## Stack y dependencias

| Área | Tecnología |
|---|---|
| Framework | **Next.js 16.2.10** (exacto) + React **19.2.4** |
| Lenguaje | TypeScript ^5 (`strict`) |
| Estilos | **Tailwind CSS 4** (config CSS‑first) + `@tailwindcss/postcss` |
| Componentes | **shadcn** ^4 + **Base UI** (`@base-ui/react`) + `lucide-react` |
| Formularios | `react-hook-form` + `@hookform/resolvers` + **zod** ^4 |
| Subida archivos | `cloudinary` ^2.10 |
| Tema | `next-themes` (instalado, **sin uso**) |
| Herramientas | `bun`, React Compiler (`reactCompiler: true`), ESLint 9 (flat) |

No hay biblioteca de estado global ni cliente HTTP externo: se usa `fetch` nativo.

## Estructura del directorio `src/`

```
src/
├── app/                      # App Router
│   ├── api/
│   │   ├── auth/clear-session/route.ts   # limpia cookies en 401/403 → /login
│   │   └── upload/route.ts               # sube a Cloudinary (multipart)
│   ├── (auth)/               # login, register, activate-account (layout split-screen)
│   ├── (main)/               # áreas autenticadas (sidebar + header)
│   ├── layout.tsx            # layout raíz (fuentes, Toaster)
│   ├── page.tsx              # landing pública
│   └── globals.css           # Tailwind v4 + tema shadcn
├── features/                 # módulos feature-first (auth, chat, courses, dashboard, profiles, topics)
│   └── <feature>/action.ts | types.ts | schemas.ts | components/
├── graphql/                  # types + queries (GraphQL)
├── hooks/                    # use-fetch, use-mobile
├── lib/                      # api.ts, graphql.ts, cookies.ts, cloudinary.ts, utils.ts
├── proxy.ts                  # protección de rutas (Middleware de Next 16)
└── shared/
    ├── components/           # course-topics-page, types, ui/ (22 componentes shadcn/Base UI)
    └── types/                # api-response.ts, page.ts
```

## Rutas / páginas

### Públicas

| Ruta | Archivo | Descripción |
|---|---|---|
| `/` | `page.tsx` | Landing pública (nav, hero, features, footer). |

### Autenticación `(auth)`

| Ruta | Archivo | Descripción |
|---|---|---|
| `/login` | `(auth)/login/page.tsx` | `LoginForm` (email/password) + botón Google decorativo. |
| `/register` | `(auth)/register/page.tsx` | `RegisterForm` (nombre/apellido/email/password) + botón Google decorativo. |
| `/activate-account` | `(auth)/activate-account/page.tsx` | Si `token` → `TokenActivator` (activa y redirige por rol). Si `email` → pantalla "revisa tu correo" con reenvío. |

### Áreas autenticadas `(main)`

| Ruta | Rol | Descripción |
|---|---|---|
| `/student` | STUDENT | Dashboard de estudiante (**datos mock**). |
| `/student/courses` | STUDENT | Cuadrícula de cursos (GraphQL, caché 1h). |
| `/student/courses/[slug]` | STUDENT | Detalle de curso (temas). |
| `/student/courses/[slug]/[topicId]` | STUDENT | Pantalla de chat: recursos (izquierda), historial (centro), mensaje (abajo). |
| `/teacher` | TEACHER | Dashboard del profesor (perfil real + nº de cursos; 2 stats marcadas `—`). |
| `/teacher/courses` | TEACHER | Cuadrícula de cursos. |
| `/teacher/courses/[slug]` | TEACHER | Detalle: crear/editar/eliminar temas y subir recursos. |
| `/admin` | ADMIN | Dashboard de admin (mezcla datos reales con stats mock). |
| `/admin/courses` | ADMIN | Tabla de cursos + diálogo de creación. |
| `/admin/courses/[slug]` | ADMIN | Detalle de curso (comparte `CourseTopicsPage` con estudiante). |
| `/admin/users` | ADMIN | Usuarios: tabs de rol, búsqueda, paginación. |
| `/admin/users/[id]` | ADMIN | Detalle de usuario (**datos mock**). |
| `/profile` | ALL | Perfil (card, info, panel por rol, edición). |

### API Routes

| Ruta | Descripción |
|---|---|
| `GET /api/auth/clear-session` | Limpia cookies `access_token`/`refresh_token` y redirige a `/login`. |
| `POST /api/upload` | Sube archivo a Cloudinary y devuelve `{url, name, type}`. |

## Componentes destacados

- **UI (`shared/components/ui/`)**: button, dialog, alert-dialog, sidebar (723 líneas), toast (Base UI `createToastManager`), input, textarea, select, switch, separator, skeleton, tooltip, sheet, accordion, search, pagination, page-size-select, filter-tabs, google-icon.
- **Auth**: `login-form`, `register-form` (ambos con botón **Google decorativo**), `token-activator`, `resend-form`.
- **Courses**: `course-list`, `courses-table`, `course-form-dialog` (con switch "Generar descripción con IA" — **no dispara IA real**), `select-teacher`, `teacher-course-list`, etc.
- **Topics**: `teacher-topic-accordion`, `topic-form-dialog`, `topic-resource-upload`, `topics-resources`, `topic-list`.
- **Profiles**: `profiles-table`, `profile-status-switch`, `role-change-dialog`, `profile-card`, `profile-info` (datos mock), `profile-edit-form` (demo local).
- **Chat**: `chat-history`, `input-send-message`, `list-resources`, `resources-provider` (Context con `Set` de recursos seleccionados), `chat-auto-scroll` + skeletons.

## Consumo de API

**Clientes de servidor (`src/lib/`):**
- `api.ts` — REST. `BASE_URL = process.env.API_URL`. Adjunta `Authorization: Bearer <access_token>` (cookies de servidor). Ante **401/403** → `redirect("/api/auth/clear-session")`. Métodos `get/post/put/patch/delete`, devuelve `ApiResponse<T>`.
- `graphql.ts` — POST a `${API_URL}/graphql`, mismo header y manejo de 401/403.

**En llamadas:**
- REST: `/auth/*`, `/courses*`, `/topics*`, `/topic/:id/resources*`, `/profiles*`, `/users/:id/*`, `/topic/:id/chat/messages`.
- GraphQL: `getCoursesForAdmin/Student/Teacher`, `dashboard`. Hay queries de `profiles` **sin usar** en `graphql/queries/profiles.ts`.
- Server actions (`"use server"` en `features/*/action.ts`) con invalidación vía `next/cache` `updateTag` y `next: { tags, revalidate: 3600 }`.
- Subida de archivos vía `POST /api/upload` (Cloudinary) desde el cliente.

## Autenticación y sesión

- **Solo email/password.** El backend emite un par JWT que el frontend guarda en **cookies `httpOnly`** (`src/lib/cookies.ts`):
  - `access_token` (maxAge 3600 s)
  - `refresh_token` (maxAge 86400 s)
  - `sameSite: lax`, `secure` en producción.
- **Refresh**: `updateSession(refreshToken)` llama a `/auth/refresh`; al éxito reasigna ambas cookies y devuelve el rol; al fallo las limpia.
- **Protección de rutas** (`src/proxy.ts`, el "Middleware" de Next 16):
  - `protectedRoutes = ["/student", "/admin", "/teacher"]`; `authRoutes = ["/login", "/register"]`.
  - Sin access pero con refresh y ruta protegida/auth → intenta `updateSession`; éxito → `/{rol}`; fallo → `/login`.
  - Ruta protegida sin access → `/login`. Ruta auth con access → `/student`.
  - `matcher` excluye `api|_next/static|_next/image|favicon.ico`.
- No hay logout (ver pendientes).

### ⚠️ Inicio de sesión con Google — NO implementado

Los botones **"Continuar con Google"** en `/login` y `/register` son **puramente decorativos**:

- Son `<Button variant="outline" type="button">` **sin `onClick`**, sin `href` y sin flujo OAuth.
- `GoogleIcon` solo se usa para mostrar el logo.

El sistema de sesión (cookies + refresh + proxy + clear-session) está completo y sería el punto de integración para implementar Google OAuth.

## Variables de entorno (`apps/frontend/.env`)

Crear un `.env`. Claves utilizadas:

| Clave | Uso |
|---|---|
| `API_URL` | URL base del backend (p. ej. `http://localhost:8080`). |
| `CLOUDINARY_CLOUD_NAME` | Nombre del cloud de Cloudinary. |
| `CLOUDINARY_API_KEY` | Clave API de Cloudinary. |
| `CLOUDINARY_API_SECRET` | Secreto API de Cloudinary. |

> Los valores están en `.env` (gitignoreado). No deben exponerse.


## Ejecución local

### Requisitos previos

- Node + **bun** (el lockfile es `bun.lock`).
- El **backend** corriendo y `API_URL` apuntando a él.
- Variables de entorno (`apps/frontend/.env`) con Cloudinary configurado.

### Comandos

```bash
cd apps/frontend
bun install        # instalar dependencias
bun dev            # desarrollo (http://localhost:3000)
bun build          # build de producción
bun start          # servir el build
bun run lint       # ESLint
```

> No hay script de tests.

## Pendientes y fixes (frontend)

- ❌ **Sesión con Google** no implementada (botones decorativos sin OAuth).
- ❌ **No hay logout** (solo expiración/limpieza de cookies en 401).
- 🧹 Dashboard de estudiante (`/student`) y stats del dashboard admin con **datos mock** hardcodeados.
- 🧹 `AdminUserDetail` (`/admin/users/[id]`) es **mock** (ids `u1`, `u2`, sin API).
- 🧹 `ProfileEditForm` es demo (guarda solo local, texto "aún no se conectan a la API"); `ProfileInfo` usa datos mock.
- 🧹 `removeCourseTeacher` es *stub* (solo `console.log`, no llama al backend).
- 🧹 Switch "Generar descripción con IA" (`useAi`) no dispara IA real (solo deshabilita el textarea).
- 🧹 `/activate-account` redirige a `/` cuando hay `email`+`token` a la vez (posible bug de guard).
- 🧹 `next-themes` instalado sin `ThemeProvider`; `GoogleIcon` duplicado en `register-form`; queries GraphQL de profiles sin usar; sin tests.
