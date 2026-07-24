import { GraduationCap, BookOpen, Users, Award } from "lucide-react";

export default function AuthLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="flex min-h-screen">
      <div className="relative hidden w-1/2 flex-col justify-between bg-primary/5 p-10 lg:flex">
        <div className="flex items-center gap-2">
          <GraduationCap className="size-6 text-primary" />
          <span className="text-lg font-semibold text-foreground">
            ClassHub
          </span>
        </div>

        <div className="space-y-4">
          <h1 className="text-3xl font-bold tracking-tight text-foreground">
            Tu plataforma
            <br />
            académica
          </h1>
          <p className="text-muted-foreground">
            Gestiona tus cursos, calificaciones y materiales de estudio
            <br />
            en un solo lugar.
          </p>
        </div>

        <div className="space-y-3 text-sm text-muted-foreground">
          <div className="flex items-center gap-3">
            <div className="flex size-9 items-center justify-center rounded-lg bg-primary/10 text-primary">
              <BookOpen size={18} />
            </div>
            <span>Accede a todos tus cursos y materiales</span>
          </div>
          <div className="flex items-center gap-3">
            <div className="flex size-9 items-center justify-center rounded-lg bg-primary/10 text-primary">
              <Users size={18} />
            </div>
            <span>Colabora con alumnos y docentes</span>
          </div>
          <div className="flex items-center gap-3">
            <div className="flex size-9 items-center justify-center rounded-lg bg-primary/10 text-primary">
              <Award size={18} />
            </div>
            <span>Rastrea tu progreso académico</span>
          </div>
        </div>
      </div>

      <div className="flex flex-1 items-center justify-center p-6">
        {children}
      </div>
    </div>
  );
}
