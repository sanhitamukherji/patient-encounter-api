# patient-encounter-api
# Jimini Health: Patient Encounter API

## 🏥 Implementation Overview
A Spring Boot service for managing HIPAA-regulated patient encounter records. The project is designed with a clear separation between API contracts, business logic, and persistence.

## 🏗 Current Architecture
- **Modern Java:** Uses **Java Records** for immutable DTOs, ensuring thread-safety and data integrity.
- **Persistence:** Leveraging **Spring Data JPA** with an **H2 In-Memory** database for a zero-config, portable demonstration environment.
- **Error Handling:** Centralized via `@RestControllerAdvice` to ensure consistent API responses and prevent internal stack leakage.
- **Identity:** Implementing a JWT-based filter chain to establish the authenticated principal.
- **Audit Trail:** AOP-based interceptors to log data access, tied to the authenticated identity.
- **PHI Protection:** Overriding DTO serialization and implementing custom logging layouts to redact sensitive identifiers from application logs.

## 🚀 Getting Started
### Prerequisites
- JDK 17
- Maven 3.x

### Execution
```bash
./mvnw clean install
./mvnw spring-boot:run