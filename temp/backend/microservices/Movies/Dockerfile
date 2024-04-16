FROM openjdk:17
EXPOSE 8012
copy target/Movies-0.0.1-SNAPSHOT.jar Movies.jar
ENTRYPOINT ["java", "-jar", "Movies.jar"]