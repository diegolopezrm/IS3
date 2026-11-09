# Proyecto final · Análisis de la plataforma académica

Análisis del proyecto de microservicios del curso. **No es código propio**: el
trabajo consistió en recorrer el sistema, ejecutar las verificaciones de las diez
guías y responder las preguntas de análisis citando el código.

- **Repositorio analizado**: https://github.com/alejandroria94/proyecto-s3
- **Commit**: `bedab2c`
- **Fecha de ejecución**: 9 de noviembre de 2026

## El informe

[INFORME-PROYECTO-FINAL.pdf](INFORME-PROYECTO-FINAL.pdf) — 24 páginas, una sección
por guía, con el diagrama de arquitectura y la propuesta de mejora.

## Evidencias

Cada archivo de [evidencias/](evidencias/) tiene la petición y la respuesta
literal de cada paso de verificación:

| Archivo | Guía |
|---|---|
| `mvn-test.txt` | las 27 pruebas de los cinco servicios |
| `g1-seguridad.txt` | seguridad |
| `g2-4-modulos.txt` | auth, estudiantes y cursos |
| `g5-matriculas.txt` | matrículas |
| `g6-e2-cupos.txt` | E2 cupos |
| `g7-8-e1-e4.txt` | E1 integridad y E4 mis matrículas |
| `g9-e3-calificaciones.txt` | E3 calificaciones |
| `g10-resiliencia.txt` | los pasos que detienen un servicio a propósito |
| `g10-docker.txt` | Docker |

## Cómo reproducirlo

```bash
git clone https://github.com/alejandroria94/proyecto-s3.git
cd proyecto-s3
for s in auth estudiantes cursos matriculas calificaciones; do
  (cd $s-service && mvn -B clean test)
done
```

Para levantarlo y repetir las verificaciones, cada servicio con su jar:

```bash
(cd auth-service && java -jar target/auth-service-0.0.1-SNAPSHOT.jar)   # 8081
# ... y así con estudiantes (8082), cursos (8083), matriculas (8084), calificaciones (8085)
```

Usuarios de prueba: `admin/admin123`, `docente/docente123`, `estudiante/estudiante123`.

## Estado

Las diez guías están verificadas salvo la captura de `docker compose ps`: al
construir las imágenes se llenó el disco de la máquina y el almacén de Docker
quedó inconsistente. Las imágenes sí se construyeron y los `Dockerfile` y el
`docker-compose.yml` se analizaron igual; queda pendiente levantar el conjunto.
