# Lab 03 — Sistema Académico + Refactor por módulos con Ports

Continúa el proyecto `demo-academico` del Lab 02 (Opción A: DTO + Handler +
`ApiResponse`). Agrega el flujo académico `Programa → Asignatura → Grupo →
Matrícula` y reorganiza el código en módulos que se comunican por interfaces
públicas.

## Cómo ejecutar

```bash
mvn clean package
java -jar target/demo-academico-0.0.1-SNAPSHOT.jar
```

O directamente con Maven:

```bash
mvn spring-boot:run
```

La aplicación usa el puerto 8080 por defecto. Para cambiarlo:

```bash
java -jar target/demo-academico-0.0.1-SNAPSHOT.jar --server.port=8081
```

## URLs

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| Consola H2 | http://localhost:8080/h2-console |

Datos de conexión de H2: JDBC `jdbc:h2:mem:demoacademico`, usuario `sa`, sin
contraseña.

## Endpoints

| Módulo | Endpoints |
|---|---|
| estudiantes | `GET/POST/PUT/DELETE /api/estudiantes` |
| programas | `GET/POST/PUT/DELETE /api/programas` |
| asignaturas | `GET/POST/DELETE /api/asignaturas` |
| grupos | `GET/POST/DELETE /api/grupos` |
| matriculas | `POST /api/matriculas` |

## Ports creados

El módulo `matriculas` tiene **prohibido** inyectar repositorios de otros
módulos. Para consultar estudiantes y grupos usa dos contratos públicos:

| Port | Paquete | Lo implementa |
|---|---|---|
| `EstudianteQueryPort` | `co.edu.demoacademico.estudiantes.port` | `EstudianteServiceImpl` |
| `GrupoQueryPort` | `co.edu.demoacademico.grupos.port` | `GrupoServiceImpl` |

Ambos exponen una sola operación, `obtenerPorId(Long id)`, que lanza
`NotFoundException` desde el módulo dueño de la entidad.

## Estructura

```
co.edu.demoacademico
├── common
│   ├── api          ApiResponse, ResponseBuilder
│   ├── exception    BusinessException, NotFoundException
│   └── controller   GlobalExceptionHandler
├── config           DataSeeder, DataSeederAcademico
├── estudiantes      model · repository · service · port · handler · controller · dto
├── programas        model · repository · service · handler · controller · dto
├── asignaturas      model · repository · service · handler · controller · dto
├── grupos           model · repository · service · port · handler · controller · dto
└── matriculas       model · repository · service · handler · controller · dto
```

## Reglas de matrícula

1. Estudiante y grupo deben existir, si no → `404`.
2. La pareja (estudiante, grupo) no se puede repetir → `400`.
3. No se puede superar `cupoMax` del grupo → `400`.

Las dos primeras están además respaldadas por restricciones de base de datos
(`uk_matricula_estudiante_grupo`).

## Scripts

```bash
./guion-pruebas.sh [puerto]        # los 10 casos de prueba del enunciado
./verificar-arquitectura.sh        # comprueba que matriculas solo use Ports
```

`guion-pruebas.sh` espera a que terminen los seeders antes de empezar, porque
Spring abre el puerto HTTP antes de ejecutar los `CommandLineRunner`.

## Semillas

Se configuran en `application.yml`:

```yaml
app:
  seed:
    enabled: true
    cantidad-estudiantes: 50
    cantidad-programas: 3
    cantidad-asignaturas: 10
    cantidad-grupos: 20
```
