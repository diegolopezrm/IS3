# IS3 — Ingeniería de Software III

Repositorio de laboratorios y trabajos del curso de Ingeniería de Software III (UIS).

## Estructura

```
IS3/
├── lab01/     # Lab 01 — módulo Estudiantes con arquitectura por capas + H2 en memoria
├── lab02/     # Lab 02 Opción A — DTOs + ApiResponse + Handler
├── lab02-b/   # Lab 02 Opción B — Entity directo + excepciones propias
├── lab03/     # Lab 03 — Sistema Académico + refactor por módulos con Ports
├── lab04/     # Lab 04 — microservicios con JWT, OpenFeign, filtro de seguridad y Docker
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

El Lab 04 son dos microservicios; hay que empaquetar antes de construir las
imágenes porque los Dockerfiles solo copian el jar:

```bash
cd lab04/microservicios-demoacademico
(cd auth-service && mvn clean package -DskipTests)
(cd consulta-service && mvn clean package -DskipTests)
docker compose up -d --build
```

- auth-service: http://localhost:8081/swagger-ui.html
- consulta-service: http://localhost:8082/swagger-ui.html (requiere JWT)

## Informes

| Trabajo | Informe |
|---------|---------|
| Lab 01 | [INFORME-LAB01.md](INFORME-LAB01.md) · [PDF](INFORME-LAB01.pdf) |
| Lab 02 | [INFORME.md](INFORME.md) · [PDF](INFORME.pdf) |
| Lab 03 | [lab03/INFORME-LAB03.md](lab03/INFORME-LAB03.md) · [PDF](lab03/INFORME-LAB03.pdf) · [README](lab03/README.md) |
| Lab 04 | [lab04/INFORME-LAB04.md](lab04/INFORME-LAB04.md) · [PDF](lab04/INFORME-LAB04.pdf) · [README](lab04/microservicios-demoacademico/README.md) |
| Clase 02 | [clase02/INFORME-CLASE02.md](clase02/INFORME-CLASE02.md) · [PDF](clase02/INFORME-CLASE02.pdf) |
| Clase 03 | [clase03/INFORME-CLASE03.md](clase03/INFORME-CLASE03.md) · [PDF](clase03/INFORME-CLASE03.pdf) |
| Clase 04 | [clase04/INFORME-CLASE04.md](clase04/INFORME-CLASE04.md) · [PDF](clase04/INFORME-CLASE04.pdf) |
| Taller de pruebas unitarias | [taller-pruebas-unitarias/INFORME-TALLER.md](taller-pruebas-unitarias/INFORME-TALLER.md) · [PDF](taller-pruebas-unitarias/INFORME-TALLER.pdf) |
| Taller de Jenkins con Docker | [taller-jenkins-docker/INFORME-TALLER-JENKINS.md](taller-jenkins-docker/INFORME-TALLER-JENKINS.md) · [PDF](taller-jenkins-docker/INFORME-TALLER-JENKINS.pdf) · [README](taller-jenkins-docker/README.md) |

El taller de pruebas unitarias se ejecuta aparte, no es una app:

```bash
cd taller-pruebas-unitarias
mvn clean test                          # 73 pruebas
open target/site/jacoco/index.html      # reporte de cobertura
```

El taller de Jenkins levanta su propio entorno con Compose:

```bash
cd taller-jenkins-docker/3-Material-actividades
docker compose up -d
# Jenkins en http://localhost:8081 · app desplegada en http://localhost:8001
```

## Progreso

Este repositorio se irá construyendo semana a semana con commits pequeños y trazables.
