FROM openjdk:17-alpine
LABEL author="teja jagadeep"
WORKDIR usr/src
ADD /target/swagger-server-0.0.1-SNAPSHOT.jar /usr/src/swagger-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar", "swagger-server-0.0.1-SNAPSHOT.jar"]