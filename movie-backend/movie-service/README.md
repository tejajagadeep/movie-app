# Movie Service

The **Movie Service** is a Spring Boot application designed to interact with an external movie API to fetch a list of the top 100 movies. It processes the data and returns it in a structured format for easy consumption. This service is ideal for developers who need a reliable source of top movie data for their applications.

## Features

- **Fetch Top 100 Movies:** Retrieves the latest top 100 movies from a specified external movie API.
- **Structured Data Response:** Returns movie data in a structured JSON format, making it easy to integrate with other applications.
- **Easy Integration:** Simple and straightforward endpoints to access movie data.

## Technologies Used

- **Java 17**
- **Spring Boot 3.2.4**
- **RestTemplate** for consuming external APIs
- **Lombok** for reducing boilerplate code
- **Maven** for dependency management
- **JUnit** and **Mockito** for testing

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed [Java 17](https://www.oracle.com/in/java/technologies/downloads/#java17).
- You have installed [Maven](https://maven.apache.org/download.cgi).
- You have a movie API key from a service like [The Movie External API (IMDb)](https://rapidapi.com/rapihub-rapihub-default/api/imdb-top-100-movies).

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/tejajagadeep/movie-app
   cd movie-backend/movie-service
   ```

2. **Configure Application Properties**

   Create an `application.properties` file in the `src/main/resources` directory and add your movie API key:

```properties
server.port=8081

rapid.api.key=(create account in rapid api)
rapid.api.host=imdb-top-100-movies.p.rapidapi.com
base.url=https://imdb-top-100-movies.p.rapidapi.com/
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

### Fetch Top 100 Movies

To retrieve the list of the top 100 movies, send a GET request to the following endpoint:

```http
GET http://localhost:8080/api/v1.0/public/movie/top-100-movies
```

#### Example Response

```json
[
  {
    "rank": 1,
    "title": "The Shawshank Redemption",
    "description": "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
    "image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_QL75_UX380_CR0,1,380,562_.jpg",
    "big_image": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@",
    "genre": ["Drama"],
    "thumbnail": "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UY67_CR0,0,45,67_AL_.jpg",
    "rating": "9.3",
    "id": "top1",
    "year": 1994,
    "imdbid": "tt0111161",
    "imdb_link": "https://www.imdb.com/title/tt0111161"
  },
  {
    "rank": 2,
    "title": "The Godfather",
    "description": "The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.",
    "image": "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_QL75_UY562_CR8,0,380,562_.jpg",
    "big_image": "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_QL75_UY562_CR8,0,380,562_.jpg",
    "genre": ["Crime", "Drama"],
    "thumbnail": "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_UY67_CR1,0,45,67_AL_.jpg",
    "rating": "9.2",
    "id": "top2",
    "year": 1972,
    "imdbid": "tt0068646",
    "imdb_link": "https://www.imdb.com/title/tt0068646"
  }
]
```

## Project Structure

```
movieservice
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── cts
│   │   │           └── movieservice
│   │   │               ├── aspects
│   │   │               │   └── LoggingAspects.java
│   │   │               │   └── PerformanceTrackerHandler.java
│   │   │               ├── configuration
│   │   │               │   └── AppConfig.java
│   │   │               │   └── CorsConfig.java
│   │   │               ├── controller
│   │   │               │   └── MovieController.java
│   │   │               ├── dto
│   │   │               │   └── Movie.java
│   │   │               │   └── MovieDetails.java
│   │   │               │   └── Response.java
│   │   │               ├── service
│   │   │               │   └── Movie.java
│   │   │               │   └── MovieDetails.java
│   │   │               │   └── Response.java
│   │   │               ├── exception
│   │   │               │   └── ErrorResponse.java
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               │   └── ResourceNotFoundException.java
│   │   │               ├── service
│   │   │               │   └── MovieService.java
│   │   │               │   └── MovieServiceImpl.java
│   │   │               └── MovieserviceApplication.java
│   │   └── resources
│   │       └── static
│   │   │       └── movie-data.json
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── cts
│                   └── movieservice
│                       ├── controller
│                       │   └── MovieControllerTest.java
│                       ├── dto
│                       │   └── MovieDetailsDiffblueTest.java
│                       │   └── MovieDiffblueTest.java
│                       ├── exception
│                       │   └── ErrorResponseJunitTest.java
│                       │   └── GlobalExceptionHandlerJunitTest.java
│                       ├── service
│                       │   └── MovieServiceImplTest.java
│                       └── MovieServiceApplicationTests.java
└── pom.xml
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests. Thank You.

## License

This project is licensed under the MIT License - see the [LICENSE](../../LICENSE.md) file for details.
