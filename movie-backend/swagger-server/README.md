# Swagger Server

The **Swagger Server** is a Spring Boot application designed to provide API documentation for various microservices in the system, including authentication, user profile, wishlist, and movie services. It uses Swagger UI to create interactive API documentation that is easy to read and use.

## Features

- **Interactive API Documentation:** Provides an interactive Swagger UI for testing and understanding API endpoints.
- **Comprehensive Documentation:** Covers multiple microservices, including authentication, user profile, wishlist, and movie services.
- **Easy Integration:** Simple setup and configuration to document APIs from different microservices.

## Technologies Used

- **Java 17**
- **Spring Boot 3.2.5**
- **Springfox Swagger 3.0** for Swagger UI
- **Maven** for dependency management
- **JUnit** and **Mockito** for testing

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
- You have installed [Maven](https://maven.apache.org/download.cgi).

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/tejajagadeep/movie-app
   cd movie-backend/swagger-server
   ```

2. **Configure Application Properties**

   Create an `application.yml` file in the `src/main/resources` directory and configure your Swagger settings:

```yml
server:
  port: 8769

swagger:
  base-url: http://localhost

springdoc:
  swagger-ui:
    urls:
      - name: authentication-service
        url: ${swagger.base-url}:8090/v3/api-docs
      - name: userprofile-service
        url: ${swagger.base-url}:8092/v3/api-docs
      - name: movie-service
        url: ${swagger.base-url}:8081/v3/api-docs
      - name: wishlist-service
        url: ${swagger.base-url}:8082/v3/api-docs
```

### Running the Service

To start the service, use the following Maven command:

```bash
mvn spring-boot:run
```

By default, the service will run on `http://localhost:8081`.

### Building the Project

To build the project, run:

```bash
mvn clean install
```

## Usage

### Accessing Swagger UI

Once the Swagger Server is up and running, you can access the Swagger UI to view and interact with the API documentation:

```http
http://localhost:8081/swagger-ui/
```

### API Endpoints Documented

The Swagger Server provides documentation for the following microservices:

1. **Authentication Service**

   - **Endpoint:** `/api/auth/**`
   - **Description:** Handles user authentication including login, registration, and token management.

2. **User Profile Service**

   - **Endpoint:** `/api/userprofile/**`
   - **Description:** Manages user profiles including CRUD operations for user data.

3. **Wishlist Service**

   - **Endpoint:** `/api/wishlist/**`
   - **Description:** Stores and retrieves movies bookmarked by users.

4. **Movie Service**
   - **Endpoint:** `/api/movies/**`
   - **Description:** Fetches and returns details about the top 100 movies.

## Project Structure

```
swaggerserver
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── cloud
│   │   │           └── swaggerserver
│   │   │               └── SwaggerServerApplication.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── cloud
│                   └── swaggerserver
│                       └── SwaggerServerApplicationTests.java
└── pom.xml
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE.md) file for details.
