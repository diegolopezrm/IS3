# IS3 — Ingeniería de Software III

Repositorio de laboratorios y trabajos del curso de Ingeniería de Software III (UIS).

## Estructura

```
IS3/
├── lab01/     # Lab 01 — módulo Estudiantes con arquitectura por capas + H2 en memoria
├── lab02/     # Lab 02 Opción A — DTOs + ApiResponse + Handler
├── lab02-b/   # Lab 02 Opción B — Entity directo + excepciones propias
├── lab03/     # Lab 03 — Sistema Académico + refactor por módulos con Ports
├── clase02/   # Trabajo en Clase 02 — informe comparativo de 12 arquitecturas
├── clase03/   # Trabajo en Clase 03 — Docker y Docker Compose con MySQL
└── clase04/   # Trabajo en Clase 04 — multi-stage, volúmenes y redes
```

## Requisitos

- Java 21
- Maven 3.9+
- Docker (solo para clase03 y clase04)

## Cómo ejecutar

```bash
cd lab03   # o lab01 / lab02 / lab02-b / clase03 / clase04
mvn spring-boot:run
```

- API: http://localhost:8080/api/estudiantes
- H2 Console: http://localhost:8080/h2-console (JDBC: `jdbc:h2:mem:demoacademico`, user `sa`, sin password)
- Swagger UI (todos menos lab01): http://localhost:8080/swagger-ui.html

> Todos los proyectos usan el puerto 8080; ejecutar uno a la vez. Para cambiarlo:
> `mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"`

Los trabajos de Docker se ejecutan así:

```bash
cd clase03 && mvn clean package && docker compose up -d
cd clase04 && mvn clean package && docker build -f Dockerfile.multi -t spring-docker-multi .
```

## Informes

| Trabajo | Informe |
|---------|---------|
| Lab 01 | [INFORME-LAB01.md](INFORME-LAB01.md) · [PDF](INFORME-LAB01.pdf) |
| Lab 02 | [INFORME.md](INFORME.md) · [PDF](INFORME.pdf) |
| Lab 03 | [lab03/INFORME-LAB03.md](lab03/INFORME-LAB03.md) · [PDF](lab03/INFORME-LAB03.pdf) · [README](lab03/README.md) |
| Clase 02 | [clase02/INFORME-CLASE02.md](clase02/INFORME-CLASE02.md) · [PDF](clase02/INFORME-CLASE02.pdf) |
| Clase 03 | [clase03/INFORME-CLASE03.md](clase03/INFORME-CLASE03.md) · [PDF](clase03/INFORME-CLASE03.pdf) |
| Clase 04 | [clase04/INFORME-CLASE04.md](clase04/INFORME-CLASE04.md) · [PDF](clase04/INFORME-CLASE04.pdf) |

## Progreso

Este repositorio se irá construyendo semana a semana con commits pequeños y trazables.
