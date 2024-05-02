FROM openjdk:17-alpine
WORKDIR usr/src
ENV SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD=$GIT_TOKEN
ADD /target/config-server-0.0.1-SNAPSHOT.jar /usr/src/config-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar", "config-server-0.0.1-SNAPSHOT.jar"]