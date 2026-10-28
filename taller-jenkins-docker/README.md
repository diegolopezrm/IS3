# Taller práctico: Jenkins con Docker

Las cinco actividades del taller, de levantar Jenkins hasta que cada `git push`
que pase las pruebas quede desplegado solo.

## Cómo levantarlo

```bash
cd 3-Material-actividades
docker compose build          # la primera vez tarda 5-10 min
docker compose up -d
docker compose logs -f jenkins   # salir con Ctrl+C al ver "fully up and running"
```

Contraseña inicial de Jenkins:

```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Para bajarlo:

```bash
docker compose down       # conserva datos, imágenes y la app desplegada
docker compose down -v    # borra además los volúmenes y la configuración
```

## Puertos

El enunciado usa 8080 y 8000, pero en este equipo los tiene ocupados otro
contenedor, así que cambié **el lado del host**. Los puertos internos de los
contenedores no se tocaron.

| Qué | Guía | Aquí |
|---|---|---|
| Jenkins | 8080 | **http://localhost:8081** |
| App desplegada | 8000 | **http://localhost:8001** |
| Agentes Jenkins | 50000 | 50000 |

Si cambias un puerto, hay que cambiarlo en `docker-compose.yml` y nada más: el
`Jenkinsfile` usa siempre los puertos internos.

## Los dos contenedores

```
jenkins          servidor web + cliente de Docker (NO tiene demonio)
jenkins-docker   Docker-in-Docker: aquí se construyen las imágenes
                 y corre la app desplegada
```

Jenkins le habla al dind por TCP con certificados (`DOCKER_HOST`,
`DOCKER_CERT_PATH`, `DOCKER_TLS_VERIFY` en el compose). La prueba de que la
conexión está viva:

```bash
docker exec jenkins docker version   # deben salir Client y Server
```

## Los jobs

| Job | Qué hace |
|---|---|
| `actividad2-hola` | Pipeline script pegado en Jenkins: etapas, parámetros y paralelo |
| `actividad3-app` | Pipeline script from SCM: lee el Jenkinsfile del repo de la app |

El repositorio de la app es
[github.com/diegolopezrm/taller-jenkins-app](https://github.com/diegolopezrm/taller-jenkins-app),
público para que Jenkins lo clone sin credenciales.

## La app

API mínima en Java, solo con el JDK, sin frameworks:

| Ruta | Respuesta |
|---|---|
| `GET /health` | `{"status":"ok"}` |
| `GET /version` | `{"version":"1.0.0"}` |
| `GET /sumar?a=2&b=3` | `{"resultado":5}` |

Tiene 6 pruebas JUnit. El `Dockerfile` es multi-etapa: compila con Maven y la
imagen final solo lleva el JRE y el `.jar` (341 MB).

## El ciclo completo

```
git push
   │
   ▼
Pruebas            ← contenedor Maven efímero. Si falla, se detiene aquí.
   │
   ▼
Construir imagen   ← nodo de Jenkins (es quien tiene el cliente Docker)
   │
   ▼
Smoke test         ← levanta el contenedor y le pega a /health
   │
   ▼
Desplegar          ← reemplaza el contenedor en ejecución y verifica /version
```

Si las pruebas fallan, las tres etapas siguientes ni se ejecutan y la versión
anterior sigue atendiendo. Eso está comprobado en el build #7.

## Si algo falla

| Síntoma | Qué mirar |
|---|---|
| `Cannot connect to the Docker daemon` | `jenkins-docker` todavía arrancando; `docker compose restart jenkins` |
| `Invalid agent type "docker"` | Falta el plugin Docker Pipeline; `docker compose build` |
| Maven: `Could not create local repository` | Hace falta `HOME = WORKSPACE` y `-Dmaven.repo.local=.m2/repository` |
| `port is already allocated` al desplegar | `docker exec jenkins-docker docker ps` y borrar el contenedor sobrante |
| `localhost:8001` no responde | `docker compose ps` debe mostrar `8001->8000` |
| El clon falla en Jenkins | El repo debe ser público, o configurar credenciales |

Dentro de los contenedores nunca uses `localhost` para hablar con otro
servicio: usa el nombre, que aquí es `docker` para el dind.

## Evidencias

Una por actividad en [evidencias/](evidencias/), y el recorrido completo en
[INFORME-TALLER-JENKINS.pdf](INFORME-TALLER-JENKINS.pdf).
