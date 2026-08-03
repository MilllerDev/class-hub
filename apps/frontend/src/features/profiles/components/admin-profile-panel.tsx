import {
  BookOpen,
  ClipboardCheck,
  Megaphone,
  ShieldCheck,
  UserPlus,
} from "lucide-react";

const adminStats = [
  { label: "Usuarios gestionados", value: 342, icon: UserPlus },
  { label: "Cursos creados", value: 45, icon: BookOpen },
  { label: "Reportes generados", value: 12, icon: ClipboardCheck },
  { label: "Avisos publicados", value: 8, icon: Megaphone },
];

const recentActions = [
  { action: "Creó el curso «Álgebra Lineal»", date: "20 jul 2026" },
  { action: "Asignó el rol Docente a Ana Ramírez", date: "24 jul 2026" },
  { action: "Publicó el aviso de inicio de semestre", date: "26 jul 2026" },
  { action: "Desactivó la cuenta de Jorge Martínez", date: "25 jul 2026" },
];

function StatCard({
  label,
  value,
  icon: Icon,
}: {
  label: string;
  value: number;
  icon: React.ComponentType<{ className?: string }>;
}) {
  return (
    <div className="rounded-xl border border-border bg-card p-4">
      <div className="flex items-center justify-between">
        <p className="text-sm text-muted-foreground">{label}</p>
        <Icon className="size-4 text-muted-foreground" />
      </div>
      <p className="mt-2 text-2xl font-bold text-card-foreground">{value}</p>
    </div>
  );
}

export function AdminProfilePanel() {
  return (
    <section className="space-y-4">
      <div className="flex items-center gap-2">
        <ShieldCheck className="size-5 text-muted-foreground" />
        <h2 className="text-lg font-semibold text-foreground">
          Actividad administrativa
        </h2>
      </div>

      <div className="grid grid-cols-2 gap-3 lg:grid-cols-4">
        {adminStats.map((stat) => (
          <StatCard key={stat.label} {...stat} />
        ))}
      </div>

      <div className="rounded-xl border border-border bg-card p-6">
        <h3 className="text-sm font-semibold text-foreground">
          Acciones recientes
        </h3>
        <ul className="mt-3 divide-y divide-border">
          {recentActions.map((item) => (
            <li
              key={item.action}
              className="flex items-center justify-between gap-4 py-2.5 text-sm"
            >
              <span className="text-card-foreground">{item.action}</span>
              <span className="shrink-0 text-xs text-muted-foreground">
                {item.date}
              </span>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}
