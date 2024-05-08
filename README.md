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

**Note:** Make sure that the below ports are available for bash command.

## Services links

| Service                | Description                                       | URL                                                                                        |
| ---------------------- | ------------------------------------------------- | ------------------------------------------------------------------------------------------ |
| Movie-App              | Web application for the MovieApp project          | [http://localhost:4200](http://localhost:4200)                                             |
| API-GATEWAY            | Gateway for accessing APIs                        | [http://localhost:8765/actuator/info](http://localhost:8765/actuator/info)                 |
| AUTHENTICATION-SERVICE | Service for user authentication and authorization | [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) |
| USERPROFILE-SERVICE    | Service for managing user profiles                | [http://localhost:8092/swagger-ui/index.html](http://localhost:8092/swagger-ui/index.html) |
| MOVIE-SERVICE          | Service for fetching movie-related data           | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |
| WISHLIST-SERVICE       | Service for managing user wishlists               | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) |

## Servers links

| Server         | Description                                             | URL                                                                                        |
| -------------- | ------------------------------------------------------- | ------------------------------------------------------------------------------------------ |
| EUREKA-SERVER  | Discovery server for microservices                      | [http://localhost:8761](http://localhost:8761)                                             |
| CONFIG-SERVER  | Server for managing configuration properties            | [http://localhost:8888/actuator/health](http://localhost:8888/actuator/health)             |
| SWAGGER-SERVER | Server hosting Swagger UI for API documentation         | [http://localhost:8769/swagger-ui/index.html](http://localhost:8769/swagger-ui/index.html) |
| ZIPKIN-SERVER  | Distributed tracing system for monitoring microservices | [http://localhost:9411](http://localhost:9411)                                             |
| Kafka-ui       | Kafka UI for kafka server (_host:kafka,port:9093_)      | [http://localhost:9090](http://localhost:9090)                                             |

## Infrastructure Elements links

| Component | Description      | URL                                               |
| --------- | ---------------- | ------------------------------------------------- |
| zookeeper | Apache Zookeeper | [http://ip-address:2182](http://127.0.0.1:2182)   |
| kafka     | Apache Kafka     | [http://ip-address:9093](http://127.0.0.1:9093)   |
| mysqldb   | MySQL Database   | [http://ip-address:3307](http://127.0.0.1:3307)   |
| mongodb   | MongoDB Database | [http://ip-address:27018](http://127.0.0.1:27018) |

**Note:** Consider all service/server names in lowercase for docker-compose host names.

## Flow Diagram

[![Flow Diagram for services](/assets/images/application-flow-diagram.png)](https://github.com/tejajagadeep/movie-app/blob/main/assets/images/application-flow-diagram.png)
