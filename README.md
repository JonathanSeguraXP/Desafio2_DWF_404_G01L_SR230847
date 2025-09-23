# Desafio2_DWF_404_G01L_SR230847
# 📦 User Subscription API

A complete backend project built with Spring Boot that manages users and their subscriptions. This repository includes full source code, integration tests, and a clean testing profile for reproducible results.

## 📝 Project Description

This RESTful API allows you to:

- Create and retrieve users
- Create and manage subscriptions
- Activate subscriptions
- Filter subscriptions by user or active status
- Validate input data with custom rules

The project is designed for educational and demo purposes, with clear structure and automated tests to ensure reliability.

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database (in-memory)**
- **JUnit 5**
- **MockMvc**
- **Maven**
- **Swagger**

## 📦 Installation & Execution

### Prerequisites

- Java 17+
- Maven 3.5.3

### Steps

```bash
# Clone the repository
git clone https://github.com/JonathanSeguraXP/Desafio2_DWF_404_G01L_SR230847.git
cd user-subscription-api

# Run the application
mvn spring-boot:run

# Run all tests
mvn test
```

### Access H2 Console (for testing)
Visit: http://localhost:8080/h2-console Use JDBC URL: jdbc:h2:mem:testdb

## 📁 Project Structure

```bash
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── sv/
│   │   │       └── edu/
│   │   │           └── udb/
│   │   │               ├── config/
│   │   │               │   └── SwaggerConfig.java
│   │   │               ├── controller/
│   │   │               │   └── UserController.java
│   │   │               ├── dto/
│   │   │               │   ├── SubscriptionRequestDto.java
│   │   │               │   ├── SubscriptionResponseDto.java
│   │   │               │   └── UserResponseDto.java
│   │   │               ├── entity/
│   │   │               │   ├── Subscription.java
│   │   │               │   └── User.java
│   │   │               ├── exception/
│   │   │               │   ├── ErrorResponse.java
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   ├── ResourceNotFoundException.java
│   │   │               │   └── ValidationException.java
│   │   │               ├── mapper/
│   │   │               │   ├── SubscriptionMapper.java
│   │   │               │   └── UserMapper.java
│   │   │               ├── repository/
│   │   │               │   ├── SubscriptionRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── service/
│   │   │               │   ├── impl/
│   │   │               │   │   └── SubscriptionServiceImpl.java
│   │   │               │   ├── SubscriptionService.java
│   │   │               │   └── UserService.java
│   │   │               └── AplicacionUsuariosSuscripcionesApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-test.properties
│   │       └── data.sql
│
├── test/
│   └── java/
│       └── sv/
│           └── edu/
│               └── udb/
│                   ├── controller/
│                   │   ├── UserControllerTest.java
│                   │   └── SubscriptionControllerTest.java
│                   ├── repository/
│                   │   ├── UserRepositoryTest.java
│                   │   └── SubscriptionRepositoryTest.java
│                   └── service/
│                       ├── SubscriptionServiceDiagnosticTest.java
│                       ├── SubscriptionServiceTest.java
│                       └── AplicacionUsuariosSuscripcionesApplicationTest.java
│
├── .gitignore
├── .gitattributes
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## 📌 Example Endpoints
## 👤 User Endpoints
```bash
http
POST /api/users
GET /api/users
GET /api/users/{id}
Sample Request (Create User)
json
POST /api/users
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```
## 📄 Subscription Endpoints
```bash
http
POST /api/subscriptions
GET /api/subscriptions
GET /api/subscriptions/user/{userId}
PATCH /api/subscriptions/{id}/activate
Sample Request (Create Subscription)
json
POST /api/subscriptions
Content-Type: application/json

{
  "name": "Premium Plan",
  "startDate": "2025-09-22",
  "endDate": "2025-10-22",
  "userId": 1
}
```

## 🧪 Testing
All tests use the test profile with an isolated H2 database. Context is reset between tests to ensure clean state.

✅ Service Layer
SubscriptionServiceDiagnosticTest

✅ Repository Layer
UserRepositoryTest

SubscriptionRepositoryTest

✅ Controller Layer
UserControllerTest

SubscriptionControllerTest

## 👨‍💻 Author
Jonathan
