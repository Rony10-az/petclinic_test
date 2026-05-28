# Project Overview

A Spring Boot REST API for unit testing.

## Stack
- Java 21 
- Spring Boot 3.5.x
- Maven
- PostgreSQL via Spring Data JPA
- JUnit 5 + Mockito for tests
- MapStruct for DTO mapping
- Lombok 

## Conventions
- Layers: service → repository
- Services in `com.tecsupt.petclinic.service` (interface + Impl)
- DTOs in `com.tecsupt.petclinic.dto`, suffix `Request` / `Response`
- Entities in `com.tecsupt.petclinic.domain`
- Use constructor injection (no @Autowired on fields)

## Build and Test
- Build: `./mvnw clean install`
- Run tests: `./mvnw test`
- Run app: `./mvnw spring-boot:run`

## What I Want to Improve
- Code quality — reduce duplication, improve naming, modernize old patterns
- Architecture — extract layers, improve separation of concerns
- Dependencies — update outdated libraries, remove unused ones
- Observability — add logging, metrics, tracing