FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/MallMate-0.0.1-SNAPSHOT.jar MallMate.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "MallMate.jar"]