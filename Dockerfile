# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/PayMyBuddy-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./PayMyBuddy-0.0.1-SNAPSHOT-jar-with-dependencies.jar

EXPOSE 8080/tcp

CMD ["java", "-jar", "PayMyBuddy-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]