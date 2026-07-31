"use client"

import Link from "next/link"
import { usePathname } from "next/navigation"
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/shared/components/ui/sidebar"
import { BookOpen, GraduationCap, LayoutDashboard, Users } from "lucide-react"

const modules = {
  admin: {
    headerLabel: "Administración",
    footerName: "Administrador",
    footerEmail: "admin@classhub.com",
    items: [
      { href: "/admin", label: "Dashboard", icon: LayoutDashboard },
      { href: "/admin/users", label: "Usuarios", icon: Users },
      { href: "/admin/courses", label: "Cursos", icon: BookOpen },
    ],
  },
  student: {
    headerLabel: "Estudiante",
    footerName: "Carlos Méndez",
    footerEmail: "carlos.mendez@school.edu",
    items: [
      { href: "/student", label: "Dashboard", icon: LayoutDashboard },
      { href: "/student/courses", label: "Mis Cursos", icon: BookOpen },
    ],
  },
} as const

function getModule(pathname: string) {
  if (pathname.startsWith("/admin")) return modules.admin
  if (pathname.startsWith("/student")) return modules.student
  return null
}

export function AppSidebar() {
  const pathname = usePathname()
  const mod = getModule(pathname)

  if (!mod) return null

  return (
    <Sidebar collapsible="icon" variant="sidebar">
      <SidebarHeader className="border-b border-border">
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton size="lg" render={<Link href="/" />}>
              <div className="flex aspect-square size-8 items-center justify-center rounded-lg bg-primary text-primary-foreground">
                <GraduationCap className="size-4" />
              </div>
              <div className="grid flex-1 text-left text-sm leading-tight">
                <span className="truncate font-semibold">ClassHub</span>
                <span className="truncate text-xs text-muted-foreground">{mod.headerLabel}</span>
              </div>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>

      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Navegación</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {mod.items.map((item) => {
                const isActive =
                  item.href === pathname

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
                )
              })}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>

      <SidebarFooter className="border-t border-border">
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton size="lg" disabled>
              <div className="flex size-8 items-center justify-center rounded-full bg-muted text-xs font-bold text-muted-foreground">
                {mod.footerName.charAt(0)}
              </div>
              <div className="grid flex-1 text-left text-sm leading-tight">
                <span className="truncate font-semibold">{mod.footerName}</span>
                <span className="truncate text-xs text-muted-foreground">{mod.footerEmail}</span>
              </div>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarFooter>
    </Sidebar>
  )
}
