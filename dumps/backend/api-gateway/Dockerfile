FROM openjdk:17-alpine
WORKDIR usr/src
ADD /target/api-gateway-0.0.1-SNAPSHOT.jar /usr/src/api-gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]