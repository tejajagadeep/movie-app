FROM openjdk:17-alpine
WORKDIR usr/src
COPY /target/movie-service-0.0.1-SNAPSHOT.jar /usr/src/movie-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar", "movie-service-0.0.1-SNAPSHOT.jar"]