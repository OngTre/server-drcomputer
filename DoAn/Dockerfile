# Step 1: Build Stage
FROM maven:3.9.1-openjdk-22 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and dependencies first (to leverage Docker caching)
COPY pom.xml .

# Download dependencies to cache them (so we don’t re-download them every time)
RUN mvn dependency:go-offline

# Copy the entire application source code
COPY DoAn/src /app/src

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Step 2: Run Stage
FROM openjdk:22-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/DoAn-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that Spring Boot runs on (default is 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]