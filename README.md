# IS3 — Ingeniería de Software III

Repositorio de laboratorios y trabajos del curso de Ingeniería de Software III (UIS).

## Estructura

```
IS3/
├── lab01/     # Lab 01 — módulo Estudiantes con arquitectura por capas + H2 en memoria
├── lab02/     # Lab 02 Opción A — DTOs + ApiResponse + Handler
└── lab02-b/   # Lab 02 Opción B — Entity directo + excepciones propias
```

## Requisitos

- Java 21
- Maven 3.9+

## Cómo ejecutar

```bash
cd lab01   # o lab02 / lab02-b
mvn spring-boot:run
```

- API: http://localhost:8080/api/estudiantes
- H2 Console: http://localhost:8080/h2-console (JDBC: `jdbc:h2:mem:demoacademico`, user `sa`, sin password)
- Swagger UI (solo lab02 / lab02-b): http://localhost:8080/swagger-ui.html

> Los tres proyectos usan el puerto 8080; ejecutar uno a la vez.

## Informes

| Laboratorio | Informe |
|-------------|---------|
| Lab 01 | [INFORME-LAB01.md](INFORME-LAB01.md) · [PDF](INFORME-LAB01.pdf) |
| Lab 02 | [INFORME.md](INFORME.md) · [PDF](INFORME.pdf) |

## Progreso

Este repositorio se irá construyendo semana a semana con commits pequeños y trazables.
