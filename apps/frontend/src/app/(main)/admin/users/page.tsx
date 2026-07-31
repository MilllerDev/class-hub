import Link from "next/link";

const users = [
  { id: "u1", name: "Lucía Fernández", email: "lucia.f@school.edu", role: "Estudiante", status: "Activo", date: "28 jul 2026" },
  { id: "u2", name: "Pedro Sánchez", email: "pedro.s@school.edu", role: "Docente", status: "Activo", date: "27 jul 2026" },
  { id: "u3", name: "María López", email: "maria.l@school.edu", role: "Estudiante", status: "Activo", date: "26 jul 2026" },
  { id: "u4", name: "Jorge Martínez", email: "jorge.m@school.edu", role: "Estudiante", status: "Inactivo", date: "25 jul 2026" },
  { id: "u5", name: "Ana Ramírez", email: "ana.r@school.edu", role: "Docente", status: "Activo", date: "24 jul 2026" },
  { id: "u6", name: "Sofía Castillo", email: "sofia.c@school.edu", role: "Estudiante", status: "Activo", date: "22 jul 2026" },
  { id: "u7", name: "Diego Navarro", email: "diego.n@school.edu", role: "Estudiante", status: "Inactivo", date: "20 jul 2026" },
  { id: "u8", name: "Marta Jiménez", email: "marta.j@school.edu", role: "Docente", status: "Activo", date: "18 jul 2026" },
];

const tabs = ["Todos", "Estudiantes", "Docentes"];

function StatusBadge({ status }: { status: string }) {
  const color = status === "Activo"
    ? "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400"
    : "bg-muted text-muted-foreground";

  return (
    <span className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}>
      {status}
    </span>
  );
}

function RoleBadge({ role }: { role: string }) {
  const color = role === "Docente"
    ? "bg-violet-100 text-violet-800 dark:bg-violet-900/30 dark:text-violet-400"
    : role === "Administrador"
      ? "bg-amber-100 text-amber-800 dark:bg-amber-900/30 dark:text-amber-400"
      : "bg-blue-100 text-blue-800 dark:bg-blue-900/30 dark:text-blue-400";

  return (
    <span className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}>
      {role}
    </span>
  );
}

export default function AdminUsersPage() {
  return (
    <div className="mx-auto w-full max-w-5xl space-y-6 px-4 py-8">
      <section>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Usuarios
        </h1>
        <p className="text-sm text-muted-foreground">
          Gestiona todos los usuarios de la plataforma.
        </p>
      </section>

      <div className="flex items-center gap-2">
        {tabs.map((tab) => (
          <span
            key={tab}
            className="inline-block cursor-default rounded-full border border-border px-3 py-1 text-xs font-medium text-muted-foreground transition-colors first:bg-primary first:text-primary-foreground first:border-primary"
          >
            {tab}
          </span>
        ))}
      </div>

      <div className="overflow-hidden rounded-xl border border-border">
        <table className="w-full text-sm">
          <thead>
            <tr className="bg-muted/50">
              <th className="px-4 py-3 text-left font-medium text-muted-foreground">Nombre</th>
              <th className="px-4 py-3 text-left font-medium text-muted-foreground hidden sm:table-cell">Email</th>
              <th className="px-4 py-3 text-left font-medium text-muted-foreground">Rol</th>
              <th className="px-4 py-3 text-left font-medium text-muted-foreground">Estado</th>
              <th className="px-4 py-3 text-left font-medium text-muted-foreground hidden md:table-cell">Registro</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-border">
            {users.map((user) => (
              <tr key={user.id} className="bg-card transition-colors hover:bg-muted/30">
                <td className="px-4 py-3">
                  <Link
                    href={`/admin/users/${user.id}`}
                    className="font-medium text-card-foreground hover:underline"
                  >
                    {user.name}
                  </Link>
                </td>
                <td className="px-4 py-3 text-muted-foreground hidden sm:table-cell">{user.email}</td>
                <td className="px-4 py-3"><RoleBadge role={user.role} /></td>
                <td className="px-4 py-3"><StatusBadge status={user.status} /></td>
                <td className="px-4 py-3 text-muted-foreground hidden md:table-cell">{user.date}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
