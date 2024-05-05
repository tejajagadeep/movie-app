FROM openjdk:17-alpine
LABEL author="teja jagadeep"
WORKDIR usr/src
ADD /target/movie-service-0.0.1-SNAPSHOT.jar /usr/src/movie-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar", "movie-service-0.0.1-SNAPSHOT.jar"]