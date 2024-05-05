FROM openjdk:17-alpine
LABEL author="tejajagadeep"
LABEL maintainer="Kollimarla Jagadeep <teja.jagadeep@gmail.com>"
LABEL documentation="https://github.com/tejajagadeep/movie-app/blob/main/README.md"
LABEL version="latest"
LABEL build_tool="Maven"
LABEL project="Movie App"
WORKDIR usr/src
ADD /target/userprofile-service-0.0.1-SNAPSHOT.jar /usr/src/userprofile-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar", "userprofile-service-0.0.1-SNAPSHOT.jar"]