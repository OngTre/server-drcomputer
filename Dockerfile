# Step 1: Build Stage
FROM maven:3-openjdk-18 AS build
WORKDIR /app

# Sao chép pom.xml và thư mục src vào container
COPY DoAn/pom.xml .  # Sao chép pom.xml từ thư mục DoAn
COPY DoAn/src /app/src  # Sao chép thư mục src từ thư mục DoAn

# Download dependencies và build ứng dụng
RUN mvn clean package -DskipTests
# Step 2: Run Stage
FROM openjdk:18-jdk-slim
WORKDIR /app
COPY --from=build /app/target/DoAn-0.0.1-SNAPSHOT.war doan.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "doan.war"]
