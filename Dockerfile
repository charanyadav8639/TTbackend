# -------- BUILD STAGE --------
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

WORKDIR /workspace

COPY . .

RUN mvn clean package -DskipTests


# -------- RUNTIME STAGE --------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]