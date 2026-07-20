# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies
RUN mvn dependency:go-offline -B
COPY src ./src
# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the built jar
COPY --from=build /app/target/*.jar app.jar
# Expose port (Render sets PORT env variable, Spring Boot uses server.port)
EXPOSE 8080
# Run the jar, mapping the PORT env var correctly for Spring
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:8080} -jar app.jar"]
