# Lyra (GreenPath) - Docker + Postgres setup

This repository contains a Spring Boot application (Lyra) prepared to run with PostgreSQL using Docker.

## Quick start (Docker)

1. Build and run using Docker Compose (will build the app image and start Postgres):

```powershell
# from project root (where docker-compose.yml is located)
docker compose up --build
```

2. The app will be available on `http://localhost:8080` and Postgres on `localhost:5432`.

3. Environment variables used by the app (set in `docker-compose.yml`):
   - `SPRING_DATASOURCE_URL` (example: `jdbc:postgresql://db:5432/lyra_db`)
   - `SPRING_DATASOURCE_USERNAME` (default: `lyra`)
   - `SPRING_DATASOURCE_PASSWORD` (default: `lyra_pass`)
   - `SPRING_PROFILES_ACTIVE` should be `postgres` when running with Postgres

   ## AI / OpenAI

   To enable OpenAI-based generation set the environment variables (or in your environment):

   - `AI_PROVIDER=openai`
   - `OPENAI_API_KEY=<your_openai_api_key>`
   - optionally `OPENAI_MODEL` (default `gpt-4o-mini`)

   The app includes a default stub generator when `AI_PROVIDER` is not set (useful for offline development).

RabbitMQ
---------

The `docker-compose.yml` now includes a `rabbitmq` service with the management UI available at `http://localhost:15672` (default user/password `guest` / `guest`). The app will wait for RabbitMQ to be available before starting.

When running locally without Docker, ensure RabbitMQ is reachable and configure the connection using the `SPRING_RABBITMQ_HOST`, `SPRING_RABBITMQ_PORT`, `SPRING_RABBITMQ_USERNAME` and `SPRING_RABBITMQ_PASSWORD` environment variables (defaults in dev are `rabbitmq:5672` and `guest/guest`).

## Local dev without Docker

You can run locally using Gradle and a local Postgres instance. Configure `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD` in your environment or in `application-postgres.properties`.

```powershell
# build
.\gradlew.bat clean build
# run with postgres profile
.\gradlew.bat bootRun --args='--spring.profiles.active=postgres'
```

## Notes
- The `Dockerfile` builds the project using the Gradle image, then copies the generated jar to a lightweight JRE base image.
- The `docker-compose.yml` uses a healthcheck for Postgres and waits for it before starting the app.

If you want, I can:
- add a `Makefile`/PowerShell helper scripts for common tasks,
- configure CI pipeline to build and push Docker images to a registry,
- add `flyway` migrations for DB schema initialization.

What should I do next?