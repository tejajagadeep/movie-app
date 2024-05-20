# Config Server

The **Config Server** is a Spring Boot application that serves as a centralized configuration management service for other microservices in the system. It provides a central location to manage external properties for applications across all environments, ensuring consistency and ease of configuration.

## Features

- **Centralized Configuration Management:** Store and manage configurations for multiple microservices from a single location.
- **Environment-Specific Configurations:** Easily manage different configurations for various environments (development, staging, production).
- **Dynamic Configuration Updates:** Supports dynamic updates to configurations without the need to restart microservices.

## Technologies Used

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Cloud Config Server**
- **Git** for storing configuration files (optional but recommended)
- **Maven** for dependency management
- **JUnit** and **Mockito** for testing

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
- You have installed [Maven](https://maven.apache.org/download.cgi).
- You have a Git repository for storing configuration files (optional but recommended).

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/tejajagadeep/movie-app
   cd movie-backend/config-service
   ```

2. **Configure Application Properties**

   Create an `application.properties` file in the `src/main/resources` directory and add your Git repository details or local file system path:

   ```properties
   spring.cloud.config.server.git.uri=https://github.com/yourusername/config-repo
   spring.cloud.config.server.git.clone-on-start=true
   ```

   Alternatively, for local file system:

   ```properties
   spring.cloud.config.server.native.search-locations=file:///path/to/your/local/config-repo
   ```

### Running the Service

To start the service, use the following Maven command:

```bash
mvn spring-boot:run
```

By default, the service will run on `http://localhost:8888`.

### Building the Project

To build the project, run:

```bash
mvn clean install
```

## Usage

### Fetch Configuration for a Microservice

To retrieve the configuration for a microservice, send a GET request to the following endpoint:

```http
GET http://localhost:8888/{application}/{profile}
```

#### Example Request

```http
GET http://localhost:8888/movie-service/dev
```

#### Example Response

```json
{
  "name": "movie-service",
  "profiles": ["dev"],
  "label": "master",
  "version": "a1b2c3d4e5f6g7h8i9j0",
  "propertySources": [
    {
      "name": "https://github.com/yourusername/config-repo/movie-service-dev.properties",
      "source": {
        "movie.api.key": "your_api_key_here",
        "movie.api.url": "https://api.themoviedb.org/3/movie/top_rated"
      }
    }
  ]
}
```

## Project Structure

```
configserver
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── cts
│   │   │           └── cloud
│   │   │               └── ConfigServerApplication.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── cts
│                   └── cloud
│                       └── ConfigServerApplicationTests.java
└── pom.xml
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE.md) file for details.
