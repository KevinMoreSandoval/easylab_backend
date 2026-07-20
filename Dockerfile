# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Limitar la memoria de Maven para evitar cuelgues (OOM) en el contenedor
ENV MAVEN_OPTS="-Xmx512m -XX:MaxMetaspaceSize=256m"

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline

COPY src src

# Compilar
RUN ./mvnw clean package -DskipTests

# Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT:-8080} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod} -jar app.jar"]
