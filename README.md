# 🚀 Distributed Job Scheduler – Backend

A **scalable distributed job scheduling system** built using **Spring Boot**, **RabbitMQ**, and **MySQL**.  
This system supports one-time and recurring jobs with retry mechanisms, failure handling, and distributed execution.

---

## 📌 Features

- One-time and recurring job scheduling
- Distributed job execution using RabbitMQ
- Automatic retries with backoff strategy
- Failure handling with retry limits
- Job status tracking and monitoring
- Distributed locking to avoid duplicate execution
- RESTful APIs for job management
- Authentication-ready architecture
- Horizontally scalable design

---

## 🏗️ High-Level Architecture

Client / UI
|
v
REST API (Spring Boot)
|
v
Scheduler (Polls due jobs)
|
v
RabbitMQ (Job Queue)
|
v
Worker Nodes (Job Execution)
|
v
Database (Job State Persistence)

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security (JWT-ready)
- Spring AMQP (RabbitMQ)

### Messaging
- RabbitMQ

### Database
- MySQL (H2 for testing)

### Testing
- JUnit 5
- Mockito
- Spring Boot Test

---

## 📂 Project Structure

src/main/java
├── config # RabbitMQ, Scheduler, Security configs
├── controller # REST controllers
├── service # Business logic
├── scheduler # Job polling & triggering
├── worker # RabbitMQ consumers
├── model # Entities & enums
├── repository # JPA repositories
└── dto # Request/response DTOs


---

## 🔁 Job Lifecycle

1. Job submitted via REST API
2. Job stored with status `PENDING`
3. Scheduler polls for due jobs
4. Distributed lock acquired
5. Job published to RabbitMQ
6. Worker consumes and executes job
7. Job status updated:
   - `PENDING`
   - `RUNNING`
   - `SUCCESS`
   - `FAILED`

---

## 🔄 Retry Strategy

- Configurable `maxRetries`
- Exponential backoff
- Automatic rescheduling
- Job marked `FAILED` after retry limit is exceeded

---

## 🔐 Authentication

- JWT-based authentication (pluggable)
- Token validation via request filters
- Role-based access can be extended

---
📈 Scalability & Reliability

Stateless REST APIs

Message-driven execution

Horizontally scalable workers

Distributed locking

Failure isolation per job.
