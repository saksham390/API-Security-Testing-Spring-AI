# Sentinel API Security Dashboard

A beginner-friendly monolithic full-stack application for testing APIs you own or are authorized to test. React provides the dashboard, Spring Boot owns validation and execution, PostgreSQL stores evidence, and Spring AI sends constrained prompts to Google Gemini.

## Architecture

```text
React + Axios
    |
Spring Boot REST API
    |-- JWT authentication + BCrypt
    |-- localhost target validation
    |-- deterministic safe test engine
    |-- Spring AI Google GenAI
    |
PostgreSQL                 Gemini
```

Gemini proposes a structured test plan or explains stored evidence. It never receives permission to execute arbitrary HTTP requests. Java validates allowed test types and the registered localhost target before the deterministic engine sends a request.

## Stack

- Java 21, Spring Boot 3.x, Maven
- Spring AI 1.1.2 Google GenAI starter
- PostgreSQL, Spring Data JPA
- Spring Security, BCrypt, JWT
- React, Vite, Axios, React Router
- Docker Compose

The frontend intentionally has no ESLint or Oxlint dependency.

## Configuration

Copy `.env.example` to `.env` and set `GEMINI_API_KEY` from Google AI Studio. Never commit `.env`. `GEMINI_API_KEY` is optional for local UI work: the app uses a small deterministic fallback plan when Gemini is unavailable, while chat explains that Gemini is not configured.

## Run with Docker

```powershell
docker compose up --build
```

Open `http://localhost:5173`. The backend is at `http://localhost:8080` and PostgreSQL is at port `5432`.

## Run the frontend without Docker

```powershell
cd frontend
npm install
npm run dev
```

Set `VITE_API_URL` if the backend is not at `http://localhost:8080/api`.

## Run the backend without Docker

Install JDK 21, Maven, and PostgreSQL first. Then set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, and optionally `GEMINI_API_KEY`.

```powershell
cd backend
mvn spring-boot:run
```

### Quick local backend mode

For a quick demo without installing PostgreSQL, use the local H2 profile. This is in-memory and resets when the application stops; the normal configuration still uses PostgreSQL.

```powershell
cd backend
mvn spring-boot:run "-Dspring-boot.run.profiles=local"
```

The local backend will be available at `http://localhost:8080`. This mode uses H2 and resets its data when the process stops. To enable Gemini in this mode, set `GEMINI_API_KEY` and remove the local `spring.ai.model.chat: none` override from `application-local.yml`.

## Main API routes

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `GET|POST /api/projects`
- `GET|PUT|DELETE /api/projects/{id}`
- `POST /api/endpoints`
- `GET /api/endpoints/{id}`
- `GET /api/endpoints/project/{projectId}`
- `POST /api/test-runs/{projectId}`
- `GET /api/test-runs/{id}/results`
- `POST /api/ai/test-plan`
- `POST /api/ai/chat`

## Security boundaries

Only `localhost` and `127.0.0.1` targets are accepted. Endpoint paths cannot contain traversal segments, backslashes, or query strings. The engine uses a fixed allowlist of non-destructive test types. Passwords, API keys, JWTs, and authorization headers are not logged.

## Verification

Frontend build:

```powershell
cd frontend
npm run build
```

Backend build and tests:

```powershell
cd backend
mvn verify
```

Health check:

```text
GET http://localhost:8080/api/health
```

Expected response:

```json
{"service":"api-security-backend","status":"UP"}
```

The backend has been verified with `mvn verify`, and the local H2 profile has been verified with `/api/health` and registration. The checked-in Java source has no language-server diagnostics.

## Publish to GitHub

1. Create an empty GitHub repository.
2. Do not commit `.env`, `backend/target`, `frontend/node_modules`, or `.tools`; they are ignored by `.gitignore`.
3. Copy `.env.example` to `.env` for local secrets.
4. Never put `GEMINI_API_KEY`, `JWT_SECRET`, database passwords, or authorization headers in GitHub.

```powershell
git init
git add .
git commit -m "Build AI-powered API security dashboard"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git
git push -u origin main
```

## Future extensions

Add richer request templates, dedicated result summary DTOs, role-specific authorization policies, printable reports, MockMvc coverage, Testcontainers integration tests, and a review/approval screen for AI plans before execution.
