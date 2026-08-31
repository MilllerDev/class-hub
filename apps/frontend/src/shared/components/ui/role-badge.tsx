const roleLabels: Record<string, string> = {
  admin: "Administrador",
  teacher: "Docente",
  student: "Estudiante",
};

const roleColors: Record<string, string> = {
  admin: "bg-amber-100 text-amber-800 dark:bg-amber-900/30 dark:text-amber-400",
  teacher:
    "bg-violet-100 text-violet-800 dark:bg-violet-900/30 dark:text-violet-400",
  student: "bg-blue-100 text-blue-800 dark:bg-blue-900/30 dark:text-blue-400",
};

export function roleLabel(role: string) {
  return roleLabels[role.toLowerCase()] ?? role;
}

export function RoleBadge({ role }: { role: string }) {
  const key = role.toLowerCase();
  const label = roleLabels[key] ?? role;
  const color = roleColors[key] ?? "bg-muted text-muted-foreground";

  return (
    <span
      className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}
    >
      {label}
    </span>
  );
}
