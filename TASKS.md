# 🐾 PetClinic — Examen de Pruebas Unitarias

**Duración:** 2.5 horas | **Stack:** Spring Boot + JUnit 5 + Mockito


---

## 📌 Reglas generales

- Repositorio en GitHub o GitLab. Rama `main` protegida.
- Cada integrante trabaja en su propia rama `feature/...`.
- Toda integración a `main` se hace por merge con al menos 1 revisor del equipo.
- Cada integrante debe tener commits propios con su nombre/correo real.
- Mínimo: **una rama por integrante, un merge fusionado por integrante y 1 conflicto resuelto**.
- Al final, ejecutar `./mvnw test` debe pasar sin errores.

---

## 🎯 Objetivo

Cada grupo debe escribir **pruebas unitarias** para los servicios de su entidad asignada, usando **JUnit 5 + Mockito**.

Cada integrante tiene asignado su propio bloque de pruebas. Cada bloque debe ir en su propia rama y PR.

---

## 👥 Escenarios para grupos de 3 integrantes

### Grupo 1 — Pruebas para OwnerService

**Integrante A — CRUD básico**
- `testCreateOwner` — crear dueño correctamente.
- `testUpdateOwner` — actualizar datos de un dueño.
- `testDeleteOwner` — eliminar dueño.

**Integrante B — Búsquedas y consultas**
- `testFindOwnerById` — buscar dueño existente.
- `testFindByLastName` — buscar por apellido.
- `testFindByCity` — buscar por ciudad.

**Integrante C — Casos de error**
- `testFindOwnerById_NotFound` — debe lanzar `OwnerNotFoundException`.
- `testDeleteOwner_NotFound` — debe lanzar excepción al borrar inexistente.
- `testFindByLastName_Empty` — devuelve lista vacía si no hay coincidencias.

---

### Grupo 2 — Pruebas para VetService

**Integrante A — CRUD básico**
- `testCreateVet` — crear veterinario.
- `testUpdateVet` — actualizar datos.
- `testFindVetById` — buscar existente.

**Integrante B — Soft delete y reactivación**
- `testDeactivateVet` — `active = false`, no se borra el registro.
- `testReactivateVet` — vuelve a activar un veterinario.
- `testFindActiveVets` — devuelve solo los activos.

**Integrante C — Casos de error y filtros**
- `testFindVetById_NotFound` — debe lanzar `VetNotFoundException`.
- `testDeactivateVet_NotFound` — debe lanzar excepción.
- `testFindInactiveVets` — devuelve solo los inactivos.

---

### Grupo 3 — Pruebas para SpecialtyService

**Integrante A — CRUD básico**
- `testCreateSpecialty` — crear especialidad.
- `testUpdateSpecialty` — actualizar especialidad.
- `testFindSpecialtyById` — buscar existente.

**Integrante B — Búsquedas**
- `testFindByOffice` — buscar por consultorio.
- `testFindByName` — buscar por nombre.
- `testDeleteSpecialty` — eliminar especialidad.

**Integrante C — Validaciones y errores**
- `testFindSpecialtyById_NotFound` — debe lanzar `SpecialtyNotFoundException`.
- `testCreateSpecialty_InvalidSchedule` — debe lanzar `InvalidScheduleException` si `h_open >= h_close`.
- `testCreateSpecialty_InvalidHourRange` — debe fallar si las horas están fuera de 0–23.

---

### Grupo 6 — Pruebas para TypeService

**Integrante A — CRUD básico**
- `testCreateType` — crear tipo.
- `testUpdateType` — actualizar tipo.
- `testFindTypeById` — buscar existente.

**Integrante B — Reporte de mascotas por tipo**
- `testGetPetCountByType` — reporte de cantidad de mascotas por tipo.
- `testGetPetCountByType_Empty` — devuelve lista vacía si no hay tipos.
- `testDeleteType` — eliminar tipo.

**Integrante C — Filtros y errores**
- `testGetPetCountByType_ExcludeInactive` — el reporte excluye tipos con `active = false`.
- `testFindTypeById_NotFound` — debe lanzar `TypeNotFoundException`.
- `testDeleteType_NotFound` — debe lanzar excepción al borrar inexistente.

---

## 👥 Escenarios para grupos de 4 integrantes

### Grupo 4 — Pruebas para VisitService

**Integrante A — CRUD básico**
- `testCreateVisit` — registrar una visita.
- `testUpdateVisit` — actualizar una visita.
- `testFindVisitById` — buscar visita existente.

**Integrante B — Consultas por relación**
- `testFindByPetId` — buscar visitas de una mascota.
- `testFindByVetId` — buscar visitas de un veterinario.
- `testDeleteVisit` — eliminar visita.

**Integrante C — Cálculos y errores**
- `testCalculateTotalCostByPet` — sumar costos con `BigDecimal`.
- `testCalculateTotalCostByPet_NoVisits` — devuelve `BigDecimal.ZERO` si no hay visitas.
- `testFindVisitById_NotFound` — debe lanzar `VisitNotFoundException`.

**Integrante D — Consultas por fecha**
- `testFindByDateBetween` — visitas entre dos fechas.
- `testFindByVetIdAndDateBetween` — visitas de un veterinario en un rango.
- `testCountVisitsByPet` — contar visitas de una mascota.

---

### Grupo 5 — Pruebas para VetSpecialtyService

**Integrante A — Asignación**
- `testAssignSpecialtyToVet` — asignar especialidad a un veterinario.
- `testRemoveSpecialtyFromVet` — quitar especialidad.
- `testAssignDuplicate_ShouldFail` — no permitir asignar dos veces la misma.

**Integrante B — Consultas**
- `testFindSpecialtiesByVet` — listar especialidades de un veterinario.
- `testFindVetsBySpecialty` — listar veterinarios de una especialidad.
- `testFindSpecialtiesByVet_Empty` — devuelve lista vacía si no tiene asignaciones.

**Integrante C — Especialidad principal**
- `testSetPrimarySpecialty` — marcar como principal.
- `testSetPrimarySpecialty_OnlyOnePrimary` — solo una principal por veterinario.
- `testSetPrimarySpecialty_NotAssigned` — debe lanzar excepción si la especialidad no está asignada.

**Integrante D — Experiencia y certificaciones**
- `testFindVetsByMinExperience` — veterinarios con experiencia mínima en una especialidad.
- `testUpdateYearsExperience` — actualizar años de experiencia.
- `testUpdateCertificationDate` — actualizar fecha de certificación.


---

## 📏 Evaluación

| Criterio                                                | Puntos |
|---------------------------------------------------------|--------|
| Uso de Git (ramas, merge, revisiones)                   | 6      |
| Cada integrante completó sus pruebas asignadas          | 4      |
| Resolución de conflictos                                | 3      |
| Calidad de las pruebas (asserts claros, uso de Mockito) | 4      |
| Las pruebas pasan (`./mvnw test`)                       | 3      |

---

## 📦 Entregables

1. Enlace al repositorio.
2. Captura de `git log --all --graph --oneline`.
3. Sección al final del README del repo con: integrantes, pruebas que hizo cada uno y conflictos resueltos.

---
