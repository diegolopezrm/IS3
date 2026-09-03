# IS3 — Ingeniería de Software III

Repositorio de laboratorios y trabajos del curso de Ingeniería de Software III (UIS).

## Estructura

```
IS3/
└── lab02/     # Aplicativo base "demo-academico" (evoluciona a lo largo del Lab 02)
```

## Requisitos

- Java 21
- Maven 3.9+

## Cómo ejecutar el aplicativo base

```bash
cd lab02
mvn spring-boot:run
```

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console (JDBC: `jdbc:h2:mem:demoacademico`, user `sa`, sin password)

## Progreso

Este repositorio se irá construyendo semana a semana con commits pequeños y trazables.
