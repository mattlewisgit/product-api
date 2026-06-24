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


## Public Deployment (Render)

The API is deployed publicly using Render and PostgreSQL.

### Live Swagger Documentation

Swagger UI:

https://product-api-k34h.onrender.com/swagger-ui/index.html#/

OpenAPI Specification:

https://product-api-k34h.onrender.com/v3/api-docs

### Accessing the Deployment

1. Log in to Render
2. Open the **product-api** Web Service
3. View application logs under **Logs**
4. Redeploy the latest version using:

```text
Manual Deploy → Deploy Latest Commit
```

### Database

The application uses a managed PostgreSQL database hosted on Render.

Database connection details are configured using environment variables:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

### Updating the Application

After making code changes:

```bash
git add .
git commit -m "Description of changes"
git push
```

Render will automatically deploy the latest commit from GitHub.

If required, a manual deployment can be triggered from the Render dashboard.

### Local Development vs Render

Local development uses:

```text
jdbc:postgresql://localhost:5432/productdb
```

Render uses environment variables configured in the Web Service.

The application automatically switches between local and cloud database connections using:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/productdb}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:postgres}
```
