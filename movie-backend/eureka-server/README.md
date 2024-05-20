# Eureka Server

The **Eureka Server** is a Spring Boot application that acts as a service registry, where all other microservices register themselves during startup for discoverability. It enables client-side load balancing and provides failover and redundancy for your microservices architecture.

## Features

- **Service Registry:** Maintains a registry of available microservices, allowing them to find and communicate with each other.
- **Load Balancing:** Enables client-side load balancing for better distribution of traffic among service instances.
- **Failover and Redundancy:** Provides failover and redundancy to ensure high availability of services.

## Technologies Used

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Cloud Netflix Eureka Server**
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
   cd movie-backend/eureka-service
   ```

2. **Configure Application Properties**

   Create an `application.properties` file in the `src/main/resources` directory and add the necessary configurations:

   ```properties
   server.port=8761
   eureka.client.register-with-eureka=false
   eureka.client.fetch-registry=false
   eureka.instance.hostname=localhost
   ```

### Running the Service

To start the service, use the following Maven command:

```bash
mvn spring-boot:run
```

By default, the service will run on `http://localhost:8761`.

### Building the Project

To build the project, run:

```bash
mvn clean install
```

## Usage

### Accessing the Eureka Dashboard

Once the Eureka Server is up and running, you can access the Eureka dashboard to view the registered services by navigating to:

```http
http://localhost:8761
```

The dashboard provides information about the status of registered microservices, their instances, and other useful metadata.

## Project Structure

```
eurekaserver
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── university
│   │   │           └── eurekaserver
│   │   │               └── EurekaServerApplication.java
│   │   └── university
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── university
│                   └── eurekaserver
│                       └── EurekaServerApplicationTests.java
└── pom.xml
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE.md) file for details.
