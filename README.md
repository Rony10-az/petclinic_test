###### Grupo 2 — Integrante A - LUIS ANGEL DIONICIO BARTOLO
Se implementaron pruebas unitarias para el módulo VetService usando Spring Boot, JUnit 5 y base de datos H2 en memoria.
Pruebas realizadas:
testCreateVet → crear veterinario.
testUpdateVet → actualizar datos.
testFindVetById → buscar veterinario existente.
#### Tecnologías usadas
Spring Boot
JUnit 5
H2 Database
Spring Data JPA
Maven
##### Archivos trabajados
VetServiceImpl.java
VetServiceTest.java
VetRepository.java
Vet.java
###### Resultado final:
Las pruebas CRUD funcionaron correctamente validando la creación, actualización y búsqueda de veterinarios usando los datos cargados en H2.
Compila sin errores:

![Imagen1.png](src/main/java/com/tecsup/petclinic/docs/Imagen1.png)


### Integrante B - Jeronimo Rodrigo Ortiz Ortiz

- Se trabajó el bloque asignado de `VetService` para validar `testDeactivateVet`, `testReactivateVet` y `testFindActiveVets` con asistencia IA local de Tecsup. 

- La funcionalidad implementa soft delete mediante el campo `active`, permitiendo desactivar y reactivar veterinarios sin eliminar registros físicamente.  

- También se agregó la consulta de veterinarios activos usando Spring Data JPA.  

- Los archivos principales revisados fueron `VetService`, `VetServiceImpl`, `VetRepository`, `Vet` y `VetServiceTest`. 

- Durante el proceso se corrigieron problemas de integración con la rama base y falsos errores del IDE sobre código generado por MapStruct.

- La validación final se realizó con Maven, obteniendo `BUILD SUCCESS`.

#### 1. Error crítico por sobrecodigo generado durante un merge.
![ERRORES CRÍTICOS POR MALA PRAXIS](src/main/java/com/tecsup/petclinic/docs/img01-ortiz.png)

#### 2. Valicaciones correctas
![SOLUCIONADO](src/main/java/com/tecsup/petclinic/docs/img02-ortiz.png)


##### Integrante C