"use client";

import { BookOpen, LayoutDashboard, User, Users } from "lucide-react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import {
    SidebarContent,
    SidebarGroup,
    SidebarGroupContent,
    SidebarGroupLabel,
    SidebarMenu,
    SidebarMenuButton,
    SidebarMenuItem,
} from "./sidebar";

const modules = {
  admin: {
    headerLabel: "Administración",
    items: [
      { href: "/admin", label: "Dashboard", icon: LayoutDashboard },
      { href: "/admin/users", label: "Usuarios", icon: Users },
      { href: "/admin/courses", label: "Cursos", icon: BookOpen },
      { href: "/profile", label: "Mi perfil", icon: User },
    ],
  },
  student: {
    headerLabel: "Estudiante",
    items: [
      { href: "/student", label: "Dashboard", icon: LayoutDashboard },
      { href: "/student/courses", label: "Mis Cursos", icon: BookOpen },
      { href: "/profile", label: "Mi perfil", icon: User },
    ],
  },
  teacher: {
    headerLabel: "Docente",
    items: [
      { href: "/teacher", label: "Dashboard", icon: LayoutDashboard },
      { href: "/teacher/courses", label: "Mis Cursos", icon: BookOpen },
      { href: "/profile", label: "Mi perfil", icon: User },
    ],
  },
} as const;

function getModule(role: string) {
  if (role === "admin") return modules.admin;
  if (role === "student") return modules.student;
  if (role === "teacher") return modules.teacher;
}

export function NavLinkSidebar({ role }: { role: string }) {
  const pathname = usePathname();
  const mod = getModule(role.toLowerCase());
  return (
    <SidebarContent>
      <SidebarGroup>
        <SidebarGroupLabel>Navegación</SidebarGroupLabel>
        <SidebarGroupContent>
          <SidebarMenu>
            {mod?.items.map((item) => {
              const isActive = item.href === pathname;
              return (
                <SidebarMenuItem key={item.href}>
                  <SidebarMenuButton
                    render={<Link href={item.href} />}
                    isActive={isActive}
                    tooltip={item.label}
                  >
                    <item.icon />
                    <span>{item.label}</span>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              );
            })}
          </SidebarMenu>
        </SidebarGroupContent>
      </SidebarGroup>
    </SidebarContent>
  );
}
