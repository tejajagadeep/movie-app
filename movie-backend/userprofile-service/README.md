# Userprofile Service

The User Profile Service in a microservices architecture is responsible for handling user registration details and managing user profiles. This service interacts with other services, such as the Authentication Service, through a message bus to ensure that user credentials are securely processed and stored. Here’s a detailed explanation of the User Profile Service's functionality and workflow:

### User Profile Service Overview

1. **User Registration:**

   - **Data Reception:** The service receives user registration details, which typically include both credentials (like username and password) and other profile information (like name, email, address, etc.).
   - **Message Publishing:** The service extracts the user credentials from the registration details and publishes them to a message bus for further processing by the Authentication Service.
   - **Profile Storage:** The remaining user profile information is stored in a database managed by the User Profile Service.

2. **Integration with Authentication Service:**
   - **Message Bus:** The User Profile Service and Authentication Service communicate asynchronously via the message bus, ensuring that credentials are handled securely and efficiently.
   - **Credentials Publishing:** By publishing credentials to the message bus, the service ensures they are securely passed to the Authentication Service, which handles password hashing and secure storage.

### Detailed Workflow

1. **User Registration Process:**

   - **Receiving Registration Data:** When a user registers, they provide their credentials and profile information through a registration form.
   - **Data Separation:** The service separates the credentials (e.g., username and password) from the other profile information (e.g., name, email).
   - **Publishing Credentials:** The credentials are published as a message to the message bus, where the Authentication Service will consume and process them.
   - **Storing Profile Information:** The non-credential profile information is stored in the User Profile Service’s database.

2. **Profile Management:**
   - **Updating Profiles:** Users can update their profile information, such as changing their email or address. The service handles these updates by modifying the records in its database.
   - **Fetching Profiles:** When user profile information is requested (e.g., for display in a user dashboard), the service retrieves the data from its database and provides it.

### Example Flow

1. **User Registration:**

   - **User Input:** A user submits a registration form with their username, password, name, and email.
   - **Service Reception:** The User Profile Service receives the registration data.
   - **Credentials Publishing:** The service publishes the username and password to the message bus.
   - **Profile Storage:** The service stores the name and email in its database.

2. **Authentication Service Handling:**

   - **Message Consumption:** The Authentication Service consumes the message from the message bus, hashes the password, and stores the credentials securely.

3. **Profile Update:**
   - **Update Request:** A user submits a request to update their email address.
   - **Profile Modification:** The User Profile Service updates the email address in its database.

### Security and Best Practices

1. **Sensitive Data Handling:**

   - **Secure Messaging:** Ensure that the message bus is configured to securely handle the transmission of sensitive data, such as user credentials.
   - **Data Encryption:** Use encryption for sensitive data both in transit and at rest.

2. **Database Security:**

   - **Access Controls:** Implement strict access controls to the database to prevent unauthorized access.
   - **Data Integrity:** Ensure data integrity through validation and consistency checks.

3. **Data Privacy:**
   - **Minimal Data Storage:** Store only the necessary information required for the service to function.
   - **Compliance:** Ensure compliance with data protection regulations (such as GDPR) by implementing appropriate data handling and user consent mechanisms.

### Example Code Snippet

Here's an example of how the User Profile Service might handle user registration in a Node.js environment using a message bus like RabbitMQ:

```javascript
const amqp = require("amqplib/callback_api");
const mongoose = require("mongoose");

// Mongoose schema for user profiles
const userProfileSchema = new mongoose.Schema({
  username: String,
  name: String,
  email: String,
});

const UserProfile = mongoose.model("UserProfile", userProfileSchema);

// Function to handle user registration
function registerUser(userData) {
  // Extract credentials and profile information
  const { username, password, name, email } = userData;

  // Publish credentials to message bus
  amqp.connect("amqp://localhost", function (error0, connection) {
    if (error0) {
      throw error0;
    }
    connection.createChannel(function (error1, channel) {
      if (error1) {
        throw error1;
      }
      const queue = "user_credentials";
      const msg = JSON.stringify({ username, password });

      channel.assertQueue(queue, {
        durable: false,
      });
      channel.sendToQueue(queue, Buffer.from(msg));

      console.log(" [x] Sent '%s'", msg);
    });
  });

  // Store profile information in the database
  const newUserProfile = new UserProfile({ username, name, email });
  newUserProfile.save(function (err) {
    if (err) {
      console.error("Error saving user profile:", err);
    } else {
      console.log("User profile saved successfully.");
    }
  });
}

// Example registration data
const newUser = {
  username: "john_doe",
  password: "securepassword123",
  name: "John Doe",
  email: "john.doe@example.com",
};

// Register the new user
registerUser(newUser);
```

In this example, the `registerUser` function separates the credentials from the profile information, publishes the credentials to a RabbitMQ queue, and stores the profile information in a MongoDB database using Mongoose. This setup ensures that the credentials are securely handled and processed by the Authentication Service.

## application properties

```properties
#server.servlet.context-path=/userprofile-service
##h2 related
#spring.application.name=user-profile

kafka.ip.address=127.0.0.1:9092

server.port=8092
spring.jpa.hibernate.ddl-auto=update
#spring.datasource.url=jdbc:mysql://spotify-jd.ctrnjcq4sjyh.us-east-1.rds.amazonaws.com/userprofile
#spring.datasource.username=admin
#spring.datasource.password=Deepu123
spring.datasource.url=jdbc:mysql://localhost:3306/userprofileDB?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root1234
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
# Kafka Consumer Configuration
spring.kafka.consumer.enable-auto-commit=true
spring.kafka.consumer.auto-commit-interval-ms=1000 # Adjust the interval as needed


spring.kafka.producer.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
#spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

logging.level.org.apache.kafka.clients.NetworkClient=error

secret.key=d3e1b7c4f6a9d21f4e5b2c3d8e6f1a2b3c4d5e6f7a8b9c1d2e3f4a5b6c7d8e9f0
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE.md) file for details.
