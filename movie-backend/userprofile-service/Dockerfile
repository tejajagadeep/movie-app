FROM openjdk:17-alpine
LABEL author="teja jagadeep"
WORKDIR usr/src
ADD /target/userprofile-service-0.0.1-SNAPSHOT.jar /usr/src/userprofile-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar", "userprofile-service-0.0.1-SNAPSHOT.jar"]