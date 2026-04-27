# E-commerce Order Processing System

A highly efficient, scalable, and robust backend service for managing e-commerce orders. Built with Java 17 and Spring Boot, this system provides a comprehensive set of RESTful APIs for order creation, tracking, status updates, and cancellations.

## 🚀 Features

* **Create Orders:** Place complex orders with multiple items, automated total calculations, and validation.
* **Order Tracking:** Retrieve real-time order details and current processing status.
* **Status Management:** Manage order lifecycles through defined states: `PENDING`, `PROCESSING`, `SHIPPED`, `DELIVERED`, and `CANCELLED`.
* **Automated Processing Job:** A scheduled background job automatically picks up `PENDING` orders and transitions them to `PROCESSING` every 5 minutes.
* **Graceful Error Handling:** Comprehensive global exception handling for malformed JSON, invalid Enums, and missing fields.
* **API Documentation:** Interactive Swagger UI documentation.
* **Security:** Secured endpoints using Spring Security Basic Authentication.

## 🛠️ Technology Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3.2.4
* **Data Access:** Spring Data JPA (Hibernate)
* **Database:** H2 Database (In-memory, easily swappable to PostgreSQL/MySQL via application.yml)
* **Security:** Spring Security (Basic Auth)
* **Validation:** Spring Boot Validation (Jakarta)
* **Documentation:** Springdoc OpenAPI (Swagger UI)
* **Testing:** JUnit 5 & Mockito
* **Utilities:** Lombok

## 🏗️ Architecture

The project follows a standard Domain-Driven Design (DDD) approach for microservices:
* **Controllers (`com.ecommerce.orders.controller`):** Handle HTTP requests, payload validation, and REST responses.
* **Services (`com.ecommerce.orders.service`):** Contain core business logic, status transitions, and data orchestration.
* **Repositories (`com.ecommerce.orders.repository`):** Spring Data JPA interfaces for database interactions.
* **Models & DTOs (`com.ecommerce.orders.model`, `com.ecommerce.orders.dto`):** Isolate database models from API contracts.
* **Scheduled Jobs (`com.ecommerce.orders.job`):** Asynchronous workers for automated system tasks.

## ⚙️ Prerequisites

* **Java 17** installed
* **Maven 3.8+** installed

## 🚦 Getting Started

### 1. Navigate to the project directory
```bash
cd orders-backend
```

### 2. Build the Project
Execute the Maven build lifecycle to compile code, run tests, and build the final jar:
```bash
mvn clean install
```

### 3. Run the Application
Start the Spring Boot application using the embedded Tomcat server:
```bash
mvn spring-boot:run
```
The application will start on `http://localhost:8080`.

## 📖 API Documentation & Manual Testing

The application provides a built-in, interactive Swagger UI to explore and test the APIs without writing any cURL commands or using Postman.

**Access Swagger UI:**
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

**Credentials for Testing:**
* **Username:** `admin`
* **Password:** `password`

### Key Endpoints:

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/orders` | Create a new order |
| `GET` | `/api/v1/orders/{id}` | Get order details by ID |
| `GET` | `/api/v1/orders` | List all orders (supports `?status=` filter) |
| `PUT` | `/api/v1/orders/{id}/status` | Manually update order status |
| `POST` | `/api/v1/orders/{id}/cancel` | Cancel a `PENDING` order |

## 🧪 Testing

The system includes a suite of automated unit tests focusing on business logic, edge cases, and proper state transitions. To execute the test suite, run:

```bash
mvn test
```

## 🔐 Security Considerations

Currently, the system uses in-memory Basic Authentication for rapid prototyping and demonstration purposes. Before moving to a production environment, the `SecurityConfig` should be updated to integrate with an OAuth2 provider (like Keycloak or Auth0) or a custom JWT-based authentication system.
