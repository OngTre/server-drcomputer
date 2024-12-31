
FROM maven:3-openjdk-18 AS build
WORKDIR /app

COPY DoAn/pom.xml .
COPY DoAn/src /app/src

RUN mvn clean package -DskipTests

FROM openjdk:18-jdk-slim
WORKDIR /app


COPY --from=build /app/target/DoAn-0.0.1-SNAPSHOT.jar doan.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "doan.jar"]
