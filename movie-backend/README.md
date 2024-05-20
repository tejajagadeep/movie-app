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

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE.md) file for details.
