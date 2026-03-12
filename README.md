# Product API – Spring Boot REST Service

## Overview

This project is a Spring Boot REST API that provides CRUD operations for managing products and orders.

The API allows clients to:

- Create products
- Retrieve products
- Update products
- Delete products
- Create customer orders containing multiple products

The application demonstrates key backend concepts including REST API design, database persistence, entity relationships, and transaction management.

---

# Technology Stack

- Java 17
- Spring Boot 3.2
- Gradle
- Spring Web (Spring MVC)
- Spring Data JPA / Hibernate
- PostgreSQL
- Docker
- Swagger (springdoc-openapi)
- Embedded Tomcat

---

# Running the Application

## 1. Prerequisites

Ensure the following are installed:

- Java 17
- Docker
- Git

Check Java version:

``` Java --version```


---

## 2. Start PostgreSQL with Docker/Rancher

The project includes a Docker configuration for PostgreSQL.

Start the database:

``` docker compose up -d```

Verify it is running:

``` docker ps```

You should see a container named:

``` product-api-postgres```

---
## 3. Build the Project

``` ./gradlew build```

---

## 4. Run the Application

``` ./gradlew bootRun```

The API will now be running at:

url: http://localhost:8080
swagger:http://localhost:8080/swagger-ui.html

This interface allows you to:

- View all API endpoints
- Inspect request and response models
- Execute API requests directly from the browser

---

# REST API Endpoints

## Products

* GET /api/products
* GET /api/products/{id}
* POST /api/products
* PUT /api/products/{id}
* DELETE /api/products/{id}


## Orders

* GET /api/orders
* GET /api/orders/{id}
* POST /api/orders
* DELETE /api/orders/{id}
