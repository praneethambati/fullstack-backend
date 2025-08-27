# User Management Backend (Spring Boot)

A Spring Boot 3 REST API providing authentication, role-based authorization, and CRUD flows for Users, Candidates, Interviews, Calls, and Audit logs.  
Secured with JWT, integrates with PostgreSQL, and exposes clean JSON APIs for the frontend dashboard.

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL 14+ (or Docker)

### 1. Run PostgreSQL (Docker)
```bash
docker run -d --name pg \
  -e POSTGRES_DB=appdb \
  -e POSTGRES_USER=appuser \
  -e POSTGRES_PASSWORD=apppass \
  -p 5432:5432 postgres:16
```
###
# DB
```
spring.datasource.url=jdbc:postgresql://localhost:5432/appdb
spring.datasource.username=appuser
spring.datasource.password=apppass
spring.jpa.hibernate.ddl-auto=update
```
# JWT
```
app.jwt.secret={secretKey}
```
# CORS
````
app.cors.allowed-origins=http://localhost:5173
````
# Key Endpoints
## Auth
````
POST /auth/login → JWT token
````
## Users
```
GET /users – list (admin)

POST /users – create (admin)

PATCH /users/{id}/active – activate/deactivate (admin)

DELETE /users/{id} (admin)
```

## Candidates
````
GET /candidates

POST /candidates

PATCH /candidates/{id}/status?value=HIRED|NEW|INTERVIEWED|REJECTED

DELETE /candidates/{id}
````
## Interviews
````
GET /interviews

POST /interviews

PATCH /interviews/{id}/status?value=SCHEDULED|COMPLETED|IN_PROGRESS|CANCELLED

DELETE /interviews/{id}
````
## Calls
````
GET /calls

POST /calls

DELETE /calls/{id}
````
## Audits
````
GET /audits – recent activity (logins, CRUD actions, role changes)
````

## Access Control

### Roles: ADMIN, USER

### Server enforcement: Spring Security + JWT filter

### Client enforcement: frontend hides unauthorized routes, but backend is authoritative

### Audit log: records user logins and key entity changes

## Security Notes

#### Passwords hashed with BCrypt

#### Stateless JWT authentication

#### CORS restricted to frontend origin

#### CSRF disabled (stateless API)

#### Rate limiting recommended in production