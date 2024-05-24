# Wishlist Service

The **Wishlist Service** is a Spring Boot application designed to handle the storage and management of movies bookmarked by users. It interacts with a database to store user-specific wishlists, enabling users to save and retrieve their favorite movies for later viewing.

## Features

- **Store Bookmarked Movies:** Allows users to bookmark their favorite movies and stores this information in a database.
- **Retrieve Wishlists:** Enables users to retrieve their list of bookmarked movies.
- **User-Specific Data:** Maintains user-specific wishlists, ensuring each user's data is isolated and secure.

## Technologies Used

- **Java 17**
- **Spring Boot 3.2.4**
- **Spring Data JPA** for database interactions
- **H2 Database** (for development and testing)
- **MySQL/PostgreSQL** (for production)
- **Maven** for dependency management
- **JUnit** and **Mockito** for testing

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
- You have installed [Maven](https://maven.apache.org/download.cgi).
- You have set up a MySQL/PostgreSQL database if using in production.

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/tejajagadeep/movie-app
   cd movie-backend/wishlist-service
   ```

2. **Configure Application Properties**

   Create an `application.properties` file in the `src/main/resources` directory and configure your database settings:

   ```properties
        #mongodb
        spring.data.mongodb.host=localhost
        spring.data.mongodb.port=27017
        spring.data.mongodb.database=baeldung
        spring.data.mongodb.username=admin
        spring.data.mongodb.password=password
   ```

### Running the Service

To start the service, use the following Maven command:

```bash
mvn spring-boot:run
```

By default, the service will run on `http://localhost:8080`.

### Building the Project

To build the project, run:

```bash
mvn clean install
```

## Usage

### Store a Movie in the Wishlist

To add a movie to a user's wishlist, send a POST request to the following endpoint:

```http
POST http://localhost:8080/api/v1.0/private/wishlist/username
```

#### Example Request Body

```json
{
  "username": "username",
  "movies": [
    {
      "imdbid": "tt0111161",
      "title": "The Shawshank Redemption",
      "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_QL75_UX380_CR0,1,380,562_.jpg",
      "description": "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
      "genre": ["Drama"],
      "rating": 9.3,
      "year": 1994,
      "imdb_link": "https://www.imdb.com/title/tt0111161"
    }
  ]
}
```

### Retrieve a User's Wishlist

To retrieve the list of bookmarked movies for a user, send a GET request to the following endpoint:

```http
GET http://localhost:8080/api/wishlist/{userId}
```

#### Example Response

```json
[
  {
    "id": 1,
    "userId": 1,
    "movieId": "tt0111161",
    "title": "The Shawshank Redemption",
    "year": 1994,
    "genre": ["Drama"],
    "rating": "9.3"
  },
  {
    "id": 2,
    "userId": 1,
    "movieId": "tt0068646",
    "title": "The Godfather",
    "year": 1972,
    "genre": ["Crime", "Drama"],
    "rating": "9.2"
  }
]
```

## Project Structure

```
wishlistservice
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── cts
│   │   │           └── wishlistservice
│   │   │               ├── aspects
│   │   │               │   └── LoggingAspects.java
│   │   │               │   └── PerformanceTrackerHandler.java
│   │   │               ├── configuration
│   │   │               │   └── AppConfig.java
│   │   │               │   └── CorsConfig.java
│   │   │               ├── controller
│   │   │               │   └── WishlistController.java
│   │   │               ├── exception
│   │   │               │   └── ErrorResponse.java
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               │   └── ResourceNotFoundException.java
│   │   │               │   └── UnAuthorizedException.java
│   │   │               ├── model
│   │   │               │   └── WishlistItem.java
│   │   │               ├── repository
│   │   │               │   └── WishlistRepository.java
│   │   │               ├── service
│   │   │               │   └── WishlistService.java
│   │   │               │   └── WishlistServiceImpl.java
│   │   │               └── WishlistServiceApplication.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── cts
│                   └── wishlistservice
│                       ├── configuration
│                       │   └── AppConfiguration.java
│                       ├── controller
│                       │   └── WishlistControllerTest.java
│                       ├── model
│                       │   └── WishlistItemTest.java
│                       ├── model
│                       │   └── MovieDtoJunitTest.java
│                       │   └── WishlistDtoJunitTEst.java
│                       ├── exception
│                       │   └── ErrorResponseJunitTest.java
│                       │   └── GlobalExceptionHandlerJunitTest.java
│                       ├── model
│                       │   └── MovieJunitTest.java
│                       │   └── WishlistJunitTEst.java
│                       └── service
│                           └── WishlistServiceImplTest.java
│                           └── WishlistServiceApplicationTests.java
└── pom.xml
```

## application properties

```properties
server.port=8082

spring.data.mongodb.uri=mongodb://root:root1234@mongodb:27017/wishlistdb?authSource=admin
spring.cache.jcache.config=ehcache.xml

secret.key=d3e1b7c4f6a9d21f4e5b2c3d8e6f1a2b3c4d5e6f7a8b9c1d2e3f4a5b6c7d8e9f0
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE.md) file for details.
