# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

This is one module of the `sisfin-environment` workspace — see `../CLAUDE.md` for how it relates to `sisfin-transaction`, `sisfin-bypass`, `sisfin-mysql`, `sisfin-compose`, and `sisfin-config`. This file covers only what's specific to `sisfin-maintenance`.

## What this service is

Spring Boot 2.5.6 / Java 11 service. Currently a single domain — `User` (registration, authentication, session/JWT issuing) — plus a Feign-facing endpoint that `sisfin-transaction` calls to validate tokens. Runs on port `8080`. `spring-boot-starter-quartz` and `spring-boot-starter-mail` are on the classpath but nothing currently uses them (no `@Scheduled`/Quartz job or `JavaMailSender` code exists yet) — treat them as available, not as evidence of existing scheduled/email features.

## Commands

```bash
./mvnw clean package              # build (mvnw.cmd on native Windows shells)
./mvnw clean package -DskipTests  # build without tests — used in the EC2 deploy flow
./mvnw test                                                       # run all tests
./mvnw test -Dtest=UserResourceTest                                # run one test class
./mvnw test -Dtest=UserExecuteAuthenticationServiceTest#methodName # run one test method
./mvnw spring-boot:run             # run locally
```

Tests live under `src/test/java/.../sisfinmaintenance/{resources,services/user}`, using `TestConfig` (`@SpringBootTest` + `@AutoConfigureMockMvc`) as the base class, `@MockBean` to stub service dependencies, and `MockMvc` to hit controller endpoints — follow this pattern for new tests (e.g. `UserResourceTest` mocks `UserExecute*Service.execute()` and posts to `/user/...`).

Local run needs the `SISFIN_*` env vars from `../sisfin-config/sisfin-backend.env` — notably `SISFIN_BACKEND_JWT_SECRET` (JWT signing key), `SISFIN_DATABASE_MAINTENANCE_*` + `SISFIN_DATABASE_DEFAULT_HOST`/`PORT` (see `DatabaseConfig`), `SISFIN_BACKEND_CORS_*` (see `CorsConfig`), `SISFIN_BACKEND_ENVIROMENT` (active Spring profile). `bootstrap.yml`/`application.yml` point at an optional Config Server at `localhost:8888` not present in this workspace — the service starts fine without it.

Docker: `Dockerfile` copies `target/*.jar` into `eclipse-temurin:11-jdk`; `docker-compose.yaml` here runs this service alone against `../sisfin-config/sisfin-backend.env`. To run the full stack (mysql + maintenance + transaction + frontend) use `../sisfin-compose/docker-compose.yaml` instead.

Prod deploy on EC2 (see `../sisfin-artifact/application-update/`): `docker compose down` → `git pull` → `./mvnw clean package -DskipTests` → `docker build -t img_sisfin_maintenance_dev:0.1 .` → `docker compose up -d`.

## Architecture

Package root: `br.com.dfdevforge.sisfinmaintenance`, plus a **separate, non-nested** shared package `br.com.dfdevforge.common` (see "Two `common` locations" below).

- **`entities/`** — `UserEntity` (`usr_user` table, `usr_*` columns), extends the shared `common.entities.BaseEntity`, Lombok `@Data`.
- **`repositories/`** — `UserRepository extends JpaRepository<UserEntity, Long>` with `findByIdentity`/`findByEmail`.
- **`services/user/`** — `UserBaseService` (holds `userParam`, extends `common.services.BaseService`) plus one class per use case implementing `CommonService`:
  - `UserExecuteRegistrationService` — validates required fields, checks email uniqueness, saves the user
  - `UserExecuteAuthenticationService` — looks up by email, compares password, issues a JWT (`java-jwt`, `HMAC512` signed with `SISFIN_BACKEND_JWT_SECRET`, 3,000,000 ms expiry, `userIdentity` claim), then base64-wraps it (`Utils.encrypt.toBase64`) via `setSessionToken()` (defined on `common.services.BaseService`) so it lands in the response map under `"token"`
  - `UserExecuteSearchService` — lookup by identity
  Same `CommonService.execute()` template as `sisfin-transaction`: `validateUserAccess()` → `executeBusinessRule()` → `configureUserActions()` → `returnBusinessData()`.
- **`resources/UserResource`** — `POST /user/executeAuthentication` (also sets an `access_token` `HttpOnly`/`Secure`/`SameSite=Strict` cookie, 1h max-age, alongside the JSON token), `POST /user/executeLogout` (invalidates the HTTP session), `POST /user/executeRegistration`.
- **`feignservers/UserFeignServer`** — `GET /userfeignserver/{token}`, the endpoint `sisfin-transaction`'s `UserFeignClient` calls. Base64-decodes the token, verifies the JWT (same `HMAC512`/`SISFIN_BACKEND_JWT_SECRET`), throws `SessionExpiredException` on `TokenExpiredException`, then loads the user by the `userIdentity` claim.
- **`configs/`** — `CorsConfig` (`WebSecurityConfigurerAdapter`, CORS from `SISFIN_BACKEND_CORS_*` env vars, CSRF disabled), `DatabaseConfig` (manual `DataSource` bean built from `SISFIN_DATABASE_*` env vars rather than plain `spring.datasource.*` properties).
- **`commons/exceptions/`** (note: nested under `sisfinmaintenance`, not the top-level `common` package) — `ResourceExceptionHandler` (`@ControllerAdvice`): `UserNotFoundException` → 404, `UserUnauthorizedException` → 401, `RequiredFieldNotFoundException`/`EmailAlreadyRegisteredException` → 500 (via the `HttpStatus*` marker exception types in the same package). Domain-specific exceptions (`UserNotFoundException`, `UserUnauthorizedException`, `EmailAlreadyRegisteredException`, `RequiredFieldNotFoundException`, `SessionExpiredException`) live in `sisfinmaintenance/exceptions/`, one level up.

### Two `common` locations — don't confuse them

Unlike `sisfin-transaction` (which nests all shared code under its own `sisfintransaction.commons` package), this service splits shared code across **two separate roots**:

- `br.com.dfdevforge.common` — `BaseEntity`, `ResourceDataEntity`, `DatePatternEnum`, `BaseException`, `BaseService`, `CommonService`, and utils (`DateUtils`, `DecryptUtils`, `EncryptUtils`, `LogUtils`, `Utils`, `ValueUtils`). This is where the `CommonService` template-method contract and `setSessionToken()` live.
- `br.com.dfdevforge.sisfinmaintenance.commons` — service-specific plumbing: `ResourceExceptionHandler` and the `HttpStatus*` marker exceptions.

When adding shared logic, put generic/reusable pieces (entities, base classes, utils) in `common`, and HTTP/exception-handling wiring specific to this app in `sisfinmaintenance.commons`, matching the existing split.

### Health check

`GET /imrunning` (`ImRunning.java`) reports name/profile/version/timestamp; dumps `SISFIN_*` env vars when `SISFIN_BACKEND_DEBUG_ACTIVATED=true` — identical implementation to `sisfin-transaction`'s.

## Conventions

- Package/class names are English; comments and some log/exception strings are Portuguese — match the existing convention per file.
- New use-case services should follow the existing `<Domain><Action>Service` naming (e.g. `UserExecuteXService`) and implement `CommonService`, not add logic directly in the controller.
- DB naming: table `usr_user`, columns `usr_*` — a 3-letter entity prefix, same convention as `sisfin-transaction`.
