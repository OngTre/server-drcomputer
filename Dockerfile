# Step 1: Build Stage
FROM maven:3-openjdk-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run Stage
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=build /app/target/DoAn-0.0.1-SNAPSHOT.war doan.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "doan.war"]
