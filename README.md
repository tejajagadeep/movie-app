# movie-app

microservice based application integrated with imdb movie api

# running application on docker

```bash
docker-compose up
```

[Redirect to assets to find docker compose file](assets/docker-compose.yml)

for latest run

```bash
docker run -it --rm -v /var/run/docker.sock:/var/run/docker.sock --name my-movie-compose-container tejajagadeep/docker-compose-movie-container
```

```csharp
Note: make sure that the below ports are available
```

## Services links

| Service                | Description                                             | URL                                                                                        |
| ---------------------- | ------------------------------------------------------- | ------------------------------------------------------------------------------------------ |
| EUREKA-SERVER          | Discovery server for microservices                      | [http://localhost:8761](http://localhost:8761)                                             |
| CONFIG-SERVER          | Server for managing configuration properties            | [http://localhost:8888/actuator/health](http://localhost:8888/actuator/info)               |
| API-GATEWAY            | Gateway for accessing APIs                              | [http://localhost:8765/actuator/info](http://localhost:8765/actuator/info)                 |
| AUTHENTICATION-SERVICE | Service for user authentication and authorization       | [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) |
| USERPROFILE-SERVICE    | Service for managing user profiles                      | [http://localhost:8092/swagger-ui/index.html](http://localhost:8092/swagger-ui/index.html) |
| MOVIE-SERVICE          | Service for fetching movie-related data                 | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |
| WISHLIST-SERVICE       | Service for managing user wishlists                     | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) |
| SWAGGER-SERVER         | Server hosting Swagger UI for API documentation         | [http://localhost:8769/swagger-ui/index.html](http://localhost:8769/swagger-ui/index.html) |
| ZIPKIN                 | Distributed tracing system for monitoring microservices | [http://localhost:9411](http://localhost:9411)                                             |
| Kafka-ui               | Kafka UI for kafka server (host:kafka,port:9090)        | [http://localhost:9090](http://localhost:9090)                                             |
| MovieApp               | Web application for the MovieApp project                | [http://localhost:4200](http://localhost:4200)                                             |

## Flow Diagram

[![Flow Diagram for services](/assets/images/application-flow-diagram.png)](https://github.com/tejajagadeep/movie-app/blob/main/assets/images/application-flow-diagram.png)
