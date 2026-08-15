"use client";

import { listProfiles } from "@/features/profiles/action";
import { useFetch } from "@/hooks/use-fetch";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/shared/components/ui/select";

type SelectTeacherProps = {
  value?: string | null;
  onChange: (value: string | null) => void;
};

export function SelectTeacher({ value, onChange }: SelectTeacherProps) {
  const { data, loading, error } = useFetch(getProfiles);
  const profiles = (data?.success ? data.data.content : []).filter(
    (profile) => profile.user.role.toLowerCase() === "teacher",
  );

  return (
    <div className="space-y-2">
      <label htmlFor="teacher" className="text-sm font-medium">
        Docente
      </label>
      <Select
        value={value ?? null}
        onValueChange={onChange}
        items={profiles.map((profile) => ({
          value: profile.user.id,
          label: `${profile.name} ${profile.lastName}`,
        }))}
      >
        <SelectTrigger id="teacher" className="w-full" aria-label="Docente">
          <SelectValue
            placeholder={
              loading ? "Cargando docentes..." : "Seleccionar docente"
            }
          />
        </SelectTrigger>
        <SelectContent alignItemWithTrigger={false}>
          {loading && (
            <SelectItem value="" disabled>
              Cargando docentes...
            </SelectItem>
          )}
          {!loading && error && (
            <p className="px-1.5 py-1 text-xs text-destructive">
              No se pudieron cargar los docentes
            </p>
          )}
          {!loading &&
            profiles.map((profile) => (
              <SelectItem key={profile.user.id} value={profile.user.id}>
                {profile.name} {profile.lastName}
              </SelectItem>
            ))}
        </SelectContent>
      </Select>
    </div>
  );
}
