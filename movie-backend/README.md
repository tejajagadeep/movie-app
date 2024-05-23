# Movie App Backend

The **Movie App Backend** is a collection of Spring microservices that collectively provide the backend functionality for the Movie App. Each microservice has its own specific role and responsibilities within the system.

## Microservices

1. [Authentication Service](./authentication-service/README.md): Responsible for user authentication, including login, registration, and token management.

2. [Userprofile Service](./userprofile-service/README.md): Manages user profiles, including CRUD operations for user data.

3. [Wishlist Service](./wishlist-service/README.md): Stores and retrieves movies bookmarked by users.

4. [Movie Service](./movie-service/README.md): Fetches and returns details about the top movies.

5. [API Gateway](./api-gateway/README.md): Routes requests from clients to the appropriate microservices and provides API documentation using Swagger UI.

6. [Config Server](./config-server/README.md): Acts as a centralized location to store the configuration of the other microservices of the system.

7. [Eureka Server](./eureka-server/README.md): Acts as a service registry where all the other microservices register during startup for discoverability.

8. [Swagger Server](./swagger-server/README.md): Provides API documentation for all microservices using Swagger UI.

## Project Structure

```
movie-app-backend
├── authentication-service
│   └── README.md
├── userprofile-service
│   └── README.md
├── wishlist-service
│   └── README.md
├── movie-service
│   └── README.md
├── api-gateway
│   └── README.md
├── config-server
│   └── README.md
├── eureka-server
│   └── README.md
├── swagger-server
│   └── README.md
└── README.md
```

## application properties in all microservices

```properties
#eureka
#eureka.instance.ip-address=true
eureka.client.fetch-registry=true
eureka.client.register-with-eureka=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

#eureka.instance.hostname=localhost
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

management.zipkin.tracing.endpoint=http://localhost:9411/api/v2/spans
management.tracing.sampling.probability=1.0

# allowed cors urls are below
allowed.cors.urls=http://localhost:8769/,http://localhost:4201/,http://localhost:4200/
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE.md) file for details.
