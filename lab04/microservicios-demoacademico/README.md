# microservicios-demoacademico

Dos microservicios que implementan el flujo completo de autenticación con JWT:
`auth-service` emite y valida los tokens, y `consulta-service` protege la
consulta de estudiantes exigiendo uno válido.

```
Cliente ──POST /auth/login──────────────> auth-service   (busca en H2,
   ▲                                          │           valida BCrypt,
   └──────────── JWT ─────────────────────────┘           firma el JWT)

Cliente ──GET /api/estudiantes──────────> consulta-service
          Authorization: Bearer <token>       │
                                              │ JwtAuthenticationFilter
                                              ▼
                                        auth-service
                                        GET /auth/validate
                                              │
                                              ▼
                                      si valid=true, pasa
```

## Cómo ejecutar

### Opción A — local, con Maven

Hay que levantar los dos, **primero auth-service**:

```bash
cd auth-service     && mvn clean package -DskipTests && java -jar target/auth-service-0.0.1-SNAPSHOT.jar
cd consulta-service && mvn clean package -DskipTests && java -jar target/consulta-service-0.0.1-SNAPSHOT.jar
```

En local, `consulta-service` busca a `auth-service` en `http://localhost:8081`,
que es el valor de `services.auth.url` en su `application.yaml`.

### Opción B — con Docker Compose

Los Dockerfiles **no compilan**, solo copian el jar, así que hay que empaquetar
antes:

```bash
cd auth-service && mvn clean package -DskipTests && cd ..
cd consulta-service && mvn clean package -DskipTests && cd ..
docker compose up -d --build
```

Dentro de Compose, `SERVICES_AUTH_URL=http://auth-service:8081` sobrescribe la
propiedad del yaml: se usa el **nombre del servicio** y su **puerto interno**,
nunca `localhost`, porque dentro de un contenedor `localhost` es el propio
contenedor.

Para bajarlo:

```bash
docker compose down
```

## URLs

| Servicio | Puerto | Swagger | H2 Console |
|---|---|---|---|
| auth-service | 8081 | http://localhost:8081/swagger-ui.html | http://localhost:8081/h2-console |
| consulta-service | 8082 | http://localhost:8082/swagger-ui.html | http://localhost:8082/h2-console |

Datos de H2: usuario `sa`, sin contraseña. JDBC `jdbc:h2:mem:authdb` en
auth-service y `jdbc:h2:mem:demoacademico` en consulta-service. Son dos bases
distintas: cada servicio es dueño de sus datos.

## Usuarios de prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| admin | `Admin2026*` | ADMIN |
| docente | `Docente2026*` | DOCENTE |
| estudiante1 | `Estu2026*` | ESTUDIANTE |

Las contraseñas se guardan cifradas con BCrypt; en la tabla `auth_user` solo
está el hash.

## Cómo usar Authorize en Swagger

1. Abrir el Swagger de **auth-service** (8081) y ejecutar `POST /auth/login`
   con uno de los usuarios de arriba.
2. Copiar el valor del campo `token` de la respuesta. **Solo el token**, sin
   las comillas.
3. Abrir el Swagger de **consulta-service** (8082) y pulsar **Authorize**.
4. Pegar el token **sin escribir `Bearer`** delante. Swagger agrega ese prefijo
   solo, porque el esquema está declarado como `bearerAuth` de tipo `http` /
   `bearer`. Si se escribe a mano, el header saldría con `Bearer Bearer ...`
   (aunque `TokenUtils` lo tolera, no es lo correcto).
5. Ejecutar `GET /api/estudiantes`.

El token dura una hora (`jwt.expiration: 3600000`). Si vence, hay que volver a
hacer login y autorizar de nuevo.

## Endpoints

### auth-service

| Método | Ruta | Qué hace |
|---|---|---|
| POST | `/auth/login` | Valida usuario y contraseña, devuelve el JWT |
| GET | `/auth/validate?token=` | Dice si el token sirve y devuelve usuario y rol |

### consulta-service

| Método | Ruta | ¿Exige token? |
|---|---|---|
| GET | `/api/estudiantes` | Sí |
| GET | `/api/estudiantes/{id}` | Sí |
| PUT | `/api/estudiantes/{id}` | Sí |
| DELETE | `/api/estudiantes/{id}` | Sí |
| POST | `/api/estudiantes` | **No** — excluido en `shouldNotFilter` |

Swagger, `/v3/api-docs` y la consola H2 tampoco pasan por el filtro.

## Respuestas cuando falta o falla el token

| Situación | Código | Cuerpo |
|---|---|---|
| Sin header `Authorization` | 401 | `Token no enviado o formato inválido` |
| Token con formato o firma inválidos | 401 | `Token inválido o expirado` |
| Token vencido | 401 | `Token inválido o expirado` |
| auth-service caído | 401 | `Token inválido o expirado` |

El último caso es intencional: `TokenValidationService` atrapa cualquier fallo
de Feign y devuelve `false`. Si no se puede confirmar que el token sirve, se
deniega.

## Estructura

```
microservicios-demoacademico/
├── docker-compose.yml
├── auth-service/
│   ├── Dockerfile
│   └── src/main/java/co/edu/authservice/
│       ├── config/      SecurityConfig, AuthDataSeeder
│       ├── controller/  AuthController
│       ├── dto/         LoginRequest, LoginResponse
│       ├── model/       AuthUser
│       ├── repository/  AuthUserRepository
│       ├── security/    JwtUtil, TokenUtils
│       └── service/     AuthService
└── consulta-service/
    ├── Dockerfile
    └── src/main/java/co/edu/demoacademico/
        ├── api/         ApiResponse, ResponseBuilder
        ├── client/      AuthFeignClient
        ├── config/      DataSeeder, OpenApiConfig
        ├── controller/  EstudianteController, GlobalExceptionHandler
        ├── dto/         EstudianteCreateDTO, EstudianteDTO,
        │                EstudianteUpdateDTO, TokenValidationResponse
        ├── exception/   BusinessException, NotFoundException
        ├── handler/     EstudianteHandler
        ├── model/       Estudiante
        ├── repository/  EstudianteRepository
        ├── security/    JwtAuthenticationFilter, SecurityConfig, TokenUtils
        └── service/     EstudianteService, EstudianteServiceImpl,
                         TokenValidationService
```

## Si algo falla

| Síntoma | Causa probable |
|---|---|
| `/auth/validate` dice inválido un token recién emitido | Se copió mal: sobran comillas o se pegó el JSON completo |
| `/api/estudiantes` da 401 con un token bueno | `services.auth.url` apunta mal, o auth-service no está arriba |
| Funciona en local pero no en Docker | Quedó `localhost` en vez de `http://auth-service:8081` |
| Authorize no surte efecto | El token venció; hay que hacer login otra vez |
| Falla tras cambiar un puerto | El puerto hay que cambiarlo en el yaml, en `services.auth.url` y en el compose |
