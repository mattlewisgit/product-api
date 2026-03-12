# Product API – Spring Boot REST Service

## Overview
This project is a Spring Boot REST API that provides CRUD operations for managing products.

The API allows clients to:

* Create products
* Retrieve products
* Update products
* Delete products



## Technology Stack

* Java 17
* Spring Boot 3.2
* Gradle
* Spring Web (Spring MVC)
* springdoc-openapi (Swagger UI)
* Embedded Tomcat



### Running the Application



### Java 17:
Make sure you have Java Development Kit (JDK) 11 installed on your system.  Check Java version:

``` Java --version```

### Build the project: 
Install it by following the instructions on the Apache Maven website.

```./gradlew build```


### Run the application

```./gradlew bootRun```

* You should see output similar to:

Tomcat started on port 8080

Started ProductApiApplication



# API Documentation (Swagger):

Swagger UI is available at:

http://localhost:8080/swagger-ui.html

This page allows you to:

* View all available endpoints
* Inspect request and response models
* Test API requests directly from the browser



### In-Memory Storage:

Products are stored in an in-memory ConcurrentHashMap.

This means:

* Data is not persisted
* All data resets when the application restarts
* This approach was chosen for simplicity in the early phase of development.
