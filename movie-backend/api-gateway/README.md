# API-Gateway

An API Gateway serves as a critical component in a microservices architecture, acting as a single entry point for client requests to the underlying microservices. Here’s an elaboration on the role and functionality of an API Gateway, particularly focusing on JWT (JSON Web Token) validation:

### API Gateway Overview

1. **Single Entry Point:**

   - **Unified Access:** The API Gateway provides a single, unified access point for all client requests. This helps in abstracting the internal microservices architecture from the clients.
   - **Routing:** It routes the incoming requests to the appropriate microservice based on the request path, headers, or other parameters.

2. **Security and Authentication:**

   - **JWT Token Validation:** The API Gateway intercepts every incoming request and checks for the presence of a JWT token.
   - **Token Decoding:** The JWT token is decoded to verify its authenticity. This involves checking the token’s signature against a secret key or a public key if using asymmetric encryption.
   - **Token Validation:** The gateway ensures that the token is not expired and that it contains the necessary claims (like user roles or permissions) required to access the requested resource.
   - **User Authentication:** By validating the JWT token, the gateway authenticates the user and confirms their identity.

3. **Authorization:**

   - **Access Control:** Based on the claims present in the JWT token, the gateway determines if the user is authorized to access the requested resource.
   - **Role-Based Access:** It can enforce role-based access control (RBAC) by checking the user’s roles or permissions included in the JWT claims.

4. **Request Handling:**

   - **Request Transformation:** The gateway can modify incoming requests before routing them to the backend services. This can include adding headers, transforming payloads, or even modifying the request path.
   - **Response Handling:** Similarly, it can transform responses from the backend services before sending them back to the client.

5. **Rate Limiting and Throttling:**

   - **Traffic Management:** The API Gateway can implement rate limiting and throttling policies to protect the backend services from being overwhelmed by too many requests.
   - **Quotas:** It can enforce quotas on the number of API calls a user or an application can make within a specified timeframe.

6. **Monitoring and Logging:**
   - **Request Logging:** It logs all incoming requests and outgoing responses for monitoring and troubleshooting purposes.
   - **Metrics:** The gateway collects metrics such as request counts, movieResponse times, error rates, etc., providing valuable insights into the system’s performance.

### Example Flow

1. **Client Request:** A client sends a request to the API Gateway with a JWT token included in the Authorization header.
2. **Token Interception:** The API Gateway intercepts the request and extracts the JWT token from the Authorization header.

3. **Token Validation:** The gateway decodes the token and verifies its signature to ensure it’s issued by a trusted authority. It also checks the token’s expiration and other claims.

4. **Routing and Authorization:**

   - If the token is valid, the gateway checks the user’s permissions and roles from the token claims to authorize the request.
   - If authorized, the gateway routes the request to the appropriate microservice.

5. **Request Processing:** The backend microservice processes the request and sends the movieResponse back to the API Gateway.

6. **Response Handling:** The gateway may modify the movieResponse (if necessary) and then sends it back to the client.

### Benefits

- **Centralized Authentication and Authorization:** All authentication and authorization logic is centralized in one place, simplifying management.
- **Reduced Complexity for Microservices:** Microservices can focus on their core functionality without worrying about security aspects.
- **Scalability:** The gateway can scale independently, handling a large volume of requests and distributing them across various microservices.

By acting as a mediator between clients and microservices, the API Gateway ensures that only authenticated and authorized requests reach the backend services, thus enhancing the overall security and manageability of the system.

## application properties

```yml
server:
  port: 8765

spring:
  cloud:
    gateway:
      default-filters:
        - DedupeResponseHeader=Access-Control-Allow-Credentials Access-Control-Allow-Origin
      globalcors:
        corsConfigurations:
          "[/**]":
            allowedOrigins: "*"
            allowedMethods: "*"
            allowedHeaders: "*"
      routes:
        - id: USERPROFILE-SERVICE
          uri: lb://USERPROFILE-SERVICE
          predicates:
            - Path=/api/v1.0/private/userProfile/**, /api/v1.0/public/userProfile/**

        - id: MOVIE-SERVICE
          uri: lb://MOVIE-SERVICE
          predicates:
            - Path=/api/v1.0/public/movie/**

        - id: WISHLIST-SERVICE
          uri: lb://WISHLIST-SERVICE
          predicates:
            - Path=/api/v1.0/private/wishlist/**

        - id: AUTHENTICATION-SERVICE
          uri: lb://AUTHENTICATION-SERVICE
          predicates:
            - Path=/api/v1.0/public/auth/**
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
  main:
    web-application-type: reactive
secret:
  key: d3e1b7c4f6a9d21f4e5b2c3d8e6f1a2b3c4d5e6f7a8b9c1d2e3f4a5b6c7d8e9f0
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE.md) file for details.
