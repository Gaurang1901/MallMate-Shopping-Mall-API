FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
RUN addgroup --system javauser && adduser --system --no-create-home --ingroup javauser javauser
COPY --from=build /app/target/MallMate-0.0.1-SNAPSHOT.jar MallMate.jar
RUN chown -R javauser:javauser /app
USER javauser
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-jar", \
            "MallMate.jar"]