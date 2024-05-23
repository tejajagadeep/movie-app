# Authentication Service

An Authentication Service plays a crucial role in a microservices architecture by managing user credentials and generating authentication tokens. Here’s a detailed explanation of the Authentication Service's functionality and workflow:

### Authentication Service Overview

1. **User Credential Management:**

   - **Message Bus Integration:** The service consumes user credential messages from a message bus (such as Kafka, RabbitMQ, or another messaging system).
   - **Storing Credentials:** Upon receiving the credentials, it securely stores them in a database. This usually involves hashing passwords before storage to enhance security.

2. **Login Process:**

   - **Credential Validation:** When a user attempts to log in, the service validates the provided login credentials (username and password) against the stored credentials in the database.
   - **Password Hashing:** The provided password is hashed using the same hashing algorithm and compared with the stored hashed password.

3. **JWT Token Generation:**

   - **Token Creation:** If the credentials match, the service generates a JWT token. This token typically includes user identification information (like user ID, roles, and permissions) and an expiration time.
   - **Token Signing:** The token is signed using a secret key (in symmetric encryption) or a private key (in asymmetric encryption) to ensure its integrity and authenticity.

4. **Response Handling:**
   - **Successful Authentication:** If the credentials are valid, the service sends the JWT token back to the user as part of the movieResponse.
   - **Error Handling:** If the credentials are invalid, the service sends an error message indicating authentication failure.

### Detailed Workflow

1. **User Registration:**

   - **Message Consumption:** When a user registers, their credentials are sent as a message to the message bus.
   - **Credential Storage:** The Authentication Service consumes this message, hashes the password, and stores the credentials in the database.

2. **User Login:**

   - **Login Request:** A user sends a login request with their username and password to the Authentication Service.
   - **Credential Retrieval:** The service retrieves the stored credentials for the provided username from the database.
   - **Password Validation:** It hashes the provided password and compares it with the stored hashed password.
     - **Match Found:** If the passwords match, the service proceeds to generate a JWT token.
     - **Mismatch:** If the passwords do not match, the service responds with an authentication error.

3. **JWT Token Generation and Response:**
   - **Token Payload:** The JWT token payload typically includes:
     - **User ID:** Unique identifier for the user.
     - **Roles and Permissions:** Information about the user's roles and permissions.
     - **Expiration Time:** Specifies how long the token is valid.
   - **Token Signing:** The payload is signed to ensure it hasn’t been tampered with.
   - **Successful Response:** The JWT token is sent back to the user in the movieResponse.
   - **Error Response:** If validation fails, an error message is sent indicating the failure reason (e.g., "Invalid credentials").

### Security Considerations

1. **Password Hashing:**

   - **Secure Hashing Algorithms:** Use secure hashing algorithms like bcrypt, Argon2, or PBKDF2 to hash passwords before storing them.
   - **Salting:** Add a unique salt to each password before hashing to protect against rainbow table attacks.

2. **JWT Token Security:**
   - **Secret Management:** Safely manage the secret keys used for signing the tokens.
   - **Token Expiration:** Set appropriate expiration times for tokens to limit the window of potential misuse.
   - **Revocation Mechanism:** Implement a mechanism to revoke tokens in case of compromised credentials or logout.

### Example Flow

1. **User Registration:**

   - **Message Sent:** A new user registers, sending their credentials to the message bus.
   - **Message Consumption:** The Authentication Service consumes the message, hashes the password, and stores the credentials in the database.

2. **User Login:**
   - **Login Request:** The user sends their username and password to the Authentication Service.
   - **Credential Validation:** The service retrieves the stored credentials, hashes the provided password, and compares it.
   - **JWT Token Generation:** Upon successful validation, the service generates a JWT token.
   - **Response:** The service sends the JWT token back to the user. If validation fails, an error message is sent.

By managing user credentials securely and generating JWT tokens, the Authentication Service ensures that only authenticated users can access the system, providing a secure and efficient authentication mechanism in a microservices architecture.

## application properties

```properties

server.port=8090

kafka.ip.address=127.0.0.1:9092

spring.jpa.hibernate.ddl-auto=update
#spring.datasource.username=admin
#spring.datasource.password=Deepu123
#spring.datasource.url=jdbc:mysql://spotify-jd.ctrnjcq4sjyh.us-east-1.rds.amazonaws.com/authentication
spring.datasource.url=jdbc:mysql://localhost:3306/authDB?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#spring.jpa.show-sql: true

jwt.secret=e9c4171e357f0181297736ce92b5581d8e48ff3fa47376f394f387c820060dd8
jwt.expiration-milliseconds=604800000

# Kafka Consumer Configuration
spring.kafka.consumer.bootstrap-servers=localhost:9092
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*
spring.kafka.consumer.group-id=mygroup
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.enable-auto-commit=true
spring.kafka.consumer.auto-commit-interval-ms=1000

secret.key=d3e1b7c4f6a9d21f4e5b2c3d8e6f1a2b3c4d5e6f7a8b9c1d2e3f4a5b6c7d8e9f0

spring.kafka.consumer.properties.spring.json.trusted.packages=*
spring.kafka.consumer.group-id=mygroup
spring.kafka.consumer.auto-offset-reset=earliest

logging.level.org.apache.kafka.clients.NetworkClient=error
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE.md) file for details.
