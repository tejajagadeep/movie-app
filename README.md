# movie-app

microservice based application integrated with imdb movie api

# running application on docker

```bash
docker-compose up
```

[Redirect to assets to find docker compose file](assets/docker-compose.yml)

## Services links

| Service                | URL                                                                                        |
| ---------------------- | ------------------------------------------------------------------------------------------ |
| EUREKA-SERVER          | [http://localhost:8761](http://localhost:8761)                                             |
| CONFIG-SERVER          | [http://localhost:8888/actuator/info](http://localhost:8888/actuator/info)                 |
| API-GATEWAY            | [http://localhost:8765/actuator/info](http://localhost:8765/actuator/info)                 |
| AUTHENTICATION-SERVICE | [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) |
| USERPROFILE-SERVICE    | [http://localhost:8092/swagger-ui/index.html](http://localhost:8092/swagger-ui/index.html) |
| MOVIE-SERVICE          | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |
| WISHLIST-SERVICE       | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) |
| SWAGGER-SERVER         | [http://localhost:8769/swagger-ui/index.html](http://localhost:8769/swagger-ui/index.html) |
| ZIPKIN                 | [http://localhost:9411](http://localhost:9411)                                             |
