FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY rts-infra/target/* target/
ENTRYPOINT ["java", "-jar", "target/rts-infra-0.0.1-SNAPSHOT.jar"]