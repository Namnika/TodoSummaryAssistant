# Use an official Maven image to build Spring Boot App
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy pom.xml from local machine to app directory and install dependencies of pom.xml
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the souce code from src to ./src inside the container and clean package again to build the application
# -DskipTests is used to speed-up the build process
COPY src ./src
RUN mvn clean package -DskipTests

# Use an official OpenJDK image to run the application
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/todoSummary-0.0.1-SNAPSHOT.jar .

# Expose port 8080
EXPOSE 8080

# Specify the command to run the application inside the container
ENTRYPOINT [ "java", "-jar", "/app/todoSummary-0.0.1-SNAPSHOT.jar" ]