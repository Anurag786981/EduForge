# EduForge Backend

Spring Boot backend application for the EduForge Coaching Institute Management Platform.

## Tech Stack

* Java 21
* Spring Boot 3.x
* Spring Security
* Spring Data JPA
* MySQL
* Maven
* Swagger/OpenAPI

## Prerequisites

Install the following software:

* Java 21
* Maven 3.9+
* MySQL 8+

Verify installation:

```bash
java -version
mvn -version
```

## Clone Repository

```bash
git clone <repository-url>
cd backend/eduforge-api
```

## Database Configuration

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eduforge
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Run Application

Using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

Or using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

Application starts on:

```text
http://localhost:8080
```

## Swagger Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## Health Check

```text
GET /api/health
```

Expected Response:

```json
{
  "status": "UP"
}
```

## Project Structure

```text
src/main/java/com/eduforge
├── attendance
├── auth
├── common
├── config
├── dashboard
├── exception
├── fee
├── paper
├── question
└── student
```

## Future Modules

* JWT Authentication
* Student Management
* Attendance Tracking
* Fee Management
* Dashboard Analytics
* Question Paper Generation
