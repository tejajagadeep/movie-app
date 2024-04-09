FROM openjdk:17
EXPOSE 8010
copy target/Registration-0.0.1-SNAPSHOT.jar RegistrationService.jar
ENTRYPOINT ["java", "-jar", "RegistrationService.jar"]