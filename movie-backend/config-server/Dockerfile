FROM openjdk:17-alpine
LABEL author="tejajagadeep"
LABEL maintainer="Kollimarla Jagadeep <teja.jagadeep@gmail.com>"
LABEL documentation="https://github.com/tejajagadeep/movie-app/blob/main/README.md"
LABEL version="latest"
LABEL build_tool="Maven"
LABEL project="Movie App"
WORKDIR usr/src
ARG SPRING_CLOUD_CONFIG_SERVER_GITHUB_PASSWORD
ENV SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD=$SPRING_CLOUD_CONFIG_SERVER_GITHUB_PASSWORD
ADD /target/config-server-0.0.1-SNAPSHOT.jar /usr/src/config-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar", "config-server-0.0.1-SNAPSHOT.jar"]
