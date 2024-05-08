# movie-app

microservice based application integrated with imdb movie api

## Description

This is a Angular (v17.3.4) and Spring Boot (v3.2.4/3.2.5) microservices project, structured around a microservices architecture. It's designed to integrate with the IMDb API to retrieve details about the top 100 movies. This project serves as a comprehensive practice exercise, covering a broad range of technologies commonly used in developing fully-fledged Spring microservices projects, including API gateway implementation, Spring Cloud integration, and more.

Furthermore, the project includes a CI/CD pipeline managed through GitHub. Continuous Integration (CI) is handled through GitHub Actions, enabling automated testing and validation of code changes as they are pushed to the repository. For Continuous Deployment (CD), Docker is utilized for containerization, ensuring consistent deployment across environments, and a workflow is established for deploying to Amazon EC2 instances.

## running application on docker

```bash
docker-compose up
```

[Redirect to assets to find docker compose file](assets/docker-compose.yml)

for latest run

```bash
docker run -it --rm -v /var/run/docker.sock:/var/run/docker.sock --name my-movie-compose-container tejajagadeep/docker-compose-movie-container
```

**Note:** Make sure that the below ports are available for bash command.

## Services

| Service                | Description                                       | URL                                                                                        |
| ---------------------- | ------------------------------------------------- | ------------------------------------------------------------------------------------------ |
| MOVIE-APP              | Web application for the MovieApp project          | [http://localhost:4200](http://localhost:4200)                                             |
| API-GATEWAY            | Gateway for accessing APIs                        | [http://localhost:8765/actuator/info](http://localhost:8765/actuator/info)                 |
| AUTHENTICATION-SERVICE | Service for user authentication and authorization | [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) |
| USERPROFILE-SERVICE    | Service for managing user profiles                | [http://localhost:8092/swagger-ui/index.html](http://localhost:8092/swagger-ui/index.html) |
| MOVIE-SERVICE          | Service for fetching movie-related data           | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |
| WISHLIST-SERVICE       | Service for managing user wishlists               | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) |

## Servers

| Server         | Description                                             | URL                                                                                        |
| -------------- | ------------------------------------------------------- | ------------------------------------------------------------------------------------------ |
| EUREKA-SERVER  | Discovery server for microservices                      | [http://localhost:8761](http://localhost:8761)                                             |
| CONFIG-SERVER  | Server for managing configuration properties            | [http://localhost:8888/actuator/health](http://localhost:8888/actuator/health)             |
| SWAGGER-SERVER | Server hosting Swagger UI for API documentation         | [http://localhost:8769/swagger-ui/index.html](http://localhost:8769/swagger-ui/index.html) |
| ZIPKIN-SERVER  | Distributed tracing system for monitoring microservices | [http://localhost:9411](http://localhost:9411)                                             |
| KAFKA-UI       | Kafka UI for kafka server (_host:kafka,port:9093_)      | [http://localhost:9090](http://localhost:9090)                                             |

## Infrastructure Elements

| Component | Description                                              | URL                                               |
| --------- | -------------------------------------------------------- | ------------------------------------------------- |
| ZOOKEEPER | Distributed coordination service for distributed systems | [http://ip-address:2182](http://127.0.0.1:2182)   |
| KAFKA     | Distributed streaming platform                           | [http://ip-address:9093](http://127.0.0.1:9093)   |
| MYSQLDB   | Relational database management system (RDBMS)            | [http://ip-address:3307](http://127.0.0.1:3307)   |
| MONGODB   | NoSQL database system                                    | [http://ip-address:27018](http://127.0.0.1:27018) |

**Note:** Consider all service/server names in lowercase for docker-compose host names.

## Flow Diagram

[![Flow Diagram for services](/assets/images/application-flow-diagram.png)](https://github.com/tejajagadeep/movie-app/blob/main/assets/images/application-flow-diagram.png)

## The responsibilities of the microservices in the above figure are as follows:

- User Profile Service: This Service is responsible for storing user registration details. The Service publishes the user credentials sent as part of registration to the message bus and stores the remaining user profile information in the database.
- Authentication Service: This Service is responsible for consuming user credential from the message bus and storing it in the database. When a user logs in, this service validates the login credentials against the credentials stored in the database. If the credentials matches, this service generates a JWT token and sends back as response, else an error message is sent.
- Movie Service: This Service is responsible for accessing an external movie API to fetch top 100 movies and returning back as response.
- Wishlist Service: This Service is responsible for storing movies bookmarked by users in the database.
- API Gateway: This Service acts as the entry point of the system. It intercepts all the requests and validates the JWT Token before routing it to the appropriate microservices.
- Eureka Server: This Service acts as a service registry where all the other microservices registers during startup for discoverability.
- Config Server: This Service acts as

[Redirect to assets/documents to find detailed overview pdf](assets/documents/MovieApp.pdf)
