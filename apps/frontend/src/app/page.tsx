import { BarChart3, BookOpen, FolderOpen, GraduationCap } from "lucide-react";
import Link from "next/link";

const features = [
  {
    icon: BookOpen,
    title: "Cursos",
    description: "Accede a tus cursos y material de estudio.",
  },
  {
    icon: BarChart3,
    title: "Calificaciones",
    description: "Revisa tu progreso y calificaciones.",
  },
  {
    icon: GraduationCap,
    title: "Docentes",
    description: "Contacta a tus profesores directamente.",
  },
  {
    icon: FolderOpen,
    title: "Contenido",
    description: "Descarga materiales y recursos por curso.",
  },
];

export default function Home() {
  return (
    <div className="flex flex-col min-h-screen">
      <nav className="flex items-center justify-between px-6 py-4 border-b border-border">
        <span className="text-lg font-semibold text-foreground">ClassHub</span>
        <div className="flex items-center gap-4 text-sm">
          <a
            href="#features"
            className="text-muted-foreground hover:text-foreground transition-colors"
          >
            Funcionalidades
          </a>
          <a
            href="#about"
            className="text-muted-foreground hover:text-foreground transition-colors"
          >
            Acerca de
          </a>
          <a
            href="/login"
            className="px-4 py-2 rounded-lg border border-border text-muted-foreground hover:text-foreground hover:border-foreground/20 transition-colors"
          >
            Iniciar Sesión
          </a>
          <a
            href="/register"
            className="px-4 py-2 rounded-lg bg-primary text-primary-foreground text-sm font-medium hover:bg-primary/90 transition-colors"
          >
            Registrarse
          </a>
        </div>
      </nav>

      <section className="flex flex-1 flex-col items-center justify-center px-6 py-24 bg-gradient-to-b from-primary/5 via-background to-background">
        <h1 className="text-4xl md:text-5xl font-bold text-center text-foreground max-w-2xl leading-tight">
          Tu Plataforma <span className="text-primary">Académica</span>
        </h1>
        <p className="mt-4 text-lg text-muted-foreground text-center max-w-lg">
          Accede a tus cursos, calificaciones y materiales de estudio. Todo lo
          que necesitas en un solo lugar.
        </p>
        <div className="flex items-center gap-4 mt-8">
          <Link
            href="/login"
            className="px-6 py-3 rounded-lg border border-border text-sm font-medium text-muted-foreground hover:text-foreground hover:border-foreground/20 transition-colors"
          >
            Iniciar Sesión
          </Link>
          <Link
            href="/register"
            className="px-6 py-3 rounded-lg bg-primary text-primary-foreground text-sm font-medium hover:bg-primary/90 transition-colors"
          >
            Registrarse
          </Link>
        </div>
      </section>

      <section id="features" className="px-6 py-20 bg-background">
        <h2 className="text-2xl font-semibold text-center text-foreground mb-12">
          Todo lo que necesitas
        </h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 max-w-5xl mx-auto">
          {features.map((feature) => (
            <div
              key={feature.title}
              className="flex flex-col items-center text-center p-6 rounded-xl border border-border bg-card hover:shadow-md transition-shadow"
            >
              <div className="w-12 h-12 flex items-center justify-center rounded-lg bg-primary/10 text-primary mb-4">
                <feature.icon size={22} />
              </div>
              <h3 className="text-base font-semibold text-foreground mb-1">
                {feature.title}
              </h3>
              <p className="text-sm text-muted-foreground">
                {feature.description}
              </p>
            </div>
          ))}
        </div>
      </section>

      <section
        id="about"
        className="px-6 py-20 bg-gradient-to-b from-background to-primary/5"
      >
        <div className="max-w-2xl mx-auto text-center">
          <h2 className="text-2xl font-semibold text-foreground mb-4">
            Acerca de ClassHub
          </h2>
          <p className="text-muted-foreground leading-relaxed">
            ClassHub es una plataforma diseñada para ti como estudiante.
            Centraliza tus cursos, calificaciones y materiales en una interfaz
            simple e intuitiva.
          </p>
        </div>
      </section>

      <footer className="px-6 py-6 border-t border-border text-center text-sm text-muted-foreground">
        © 2026 ClassHub. Todos los derechos reservados.
      </footer>
    </div>
  );
}
