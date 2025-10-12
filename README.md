# 🧩 Task Tracking and Management System (Spring Boot)

A backend system built with **Spring Boot**, **Java 21**, and **MySQL** that enables teams and users to **create, assign, and track tasks**, manage **projects/teams**, and collaborate with comments and attachments.  
This project demonstrates **secure authentication**, **task workflows**, and **modular microservice-ready architecture** — implemented locally (no external deployment).

---

## 🚀 Features

### 🧑‍💻 User Authentication & Management
- User registration with **email verification** (using logged verification links).
- Secure password storage with **BCrypt hashing**.
- **JWT-based authentication** (access + refresh tokens).
- Login, logout, and token refresh endpoints.
- Role-based user access (User, TeamLead, Manager, etc.).
- Profile management & activity tracking (`last_login`, `created_at`, etc.).

### 📋 Task Management
- Create, update, delete, and fetch tasks.
- Assign tasks to users within teams.
- Filter, sort, and search tasks (by status, due date, assignee, etc.).
- Task status transitions (Open → In Progress → Completed).

### 👥 Team / Project Collaboration
- Create and manage teams/projects.
- Invite or remove team members.
- Assign tasks within a team context.
- Add comments and file attachments to tasks.
- Optional extension: real-time notifications and updates (WebSocket-ready).

### 🧱 Architecture
- Layered modular design:
  - **Controller Layer** (REST APIs)
  - **Service Layer** (business logic)
  - **Repository Layer** (Spring Data JPA)
  - **Entity & DTO Models**
  - **Mapper Layer** for conversions
- Extensible to microservices if needed (Auth, Task, Team).

---

## ⚙️ Tech Stack

| Layer | Technology |
|-------|-------------|
| Language | Java 21 |
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT (HS256) |
| ORM | Spring Data JPA (Hibernate) |
| Database | MySQL 8 |
| Build Tool | Gradle |
| Validation | Jakarta Validation |
| Token Management | JJWT (io.jsonwebtoken) |
| Email Service | Logging stub (prints verification link in logs) |
| Migrations | Flyway |
| Logging | SLF4J / Logback |
| Testing | JUnit + Spring Boot Test |

---

---

## 🧠 Key Workflows

### 🪪 1. Registration & Verification
1. `POST /api/auth/register` → creates inactive user + verification token.
2. Check console logs for the **verification link** (e.g., `/api/auth/verify?token=XYZ`).
3. Call verification endpoint → activates user account.

### 🔐 2. Login
`POST /api/auth/login` → returns access token, refresh token, and user info.

### ♻️ 3. Token Refresh
`POST /api/auth/refresh` → returns new access token.

### 🚪 4. Logout
`POST /api/auth/logout` → revokes refresh token.

### 📋 5. Task CRUD
- `POST /api/teams/{teamId}/tasks`
- `GET /api/tasks/{taskId}`
- `PUT /api/tasks/{taskId}`
- `DELETE /api/tasks/{taskId}`

### 👥 6. Teams & Collaboration
- `POST /api/teams` — create a team.
- `POST /api/teams/{teamId}/members` — invite members.
- `POST /api/tasks/{taskId}/comments` — add a comment.

---

## 🧰 Setup & Run Locally

### 1️⃣ Prerequisites
- Java 21+
- MySQL 8+
- Gradle (wrapper included)
- Postman (for API testing)

### 2️⃣ Database Setup
Create a database:
```sql
CREATE DATABASE auth_db;

## 🧩 Project Structure
com.example.tasktracker
├── controller
│ ├── AuthController.java
│ ├── TaskController.java
│ ├── TeamController.java
│ └── ProfileController.java
│
├── service
│ ├── impl
│ │ ├── AuthServiceImpl.java
│ │ ├── LoggingEmailService.java
│ │ ├── TaskServiceImpl.java
│ │ └── TeamServiceImpl.java
│ ├── AuthService.java
│ ├── EmailService.java
│ ├── TaskService.java
│ └── TeamService.java
│
├── repository
│ ├── UserRepository.java
│ ├── TaskRepository.java
│ ├── TeamRepository.java
│ ├── RefreshTokenRepository.java
│ └── VerificationTokenRepository.java
│
├── model
│ ├── entity
│ │ ├── User.java
│ │ ├── Task.java
│ │ ├── Team.java
│ │ ├── Comment.java
│ │ ├── Attachment.java
│ │ └── Role.java
│ └── dto
│ ├── UserDto.java
│ ├── UserRequestDto.java
│ ├── LoginRequestDto.java
│ ├── CreateTaskRequest.java
│ └── TaskResponseDto.java
│
├── config
│ ├── SecurityConfig.java
│ ├── JwtConfig.java
│ └── WebMvcConfig.java
│
├── util
│ ├── JwtTokenProvider.java
│ └── JwtTokenProviderImpl.java
│
├── exception
│ ├── GlobalExceptionHandler.java
│ ├── NotFoundException.java
│ └── ValidationException.java
│
└── TaskTrackingManagementSystemApplication.java
