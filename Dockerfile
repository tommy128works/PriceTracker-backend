# ---- Build stage ----
FROM gradle:8.7-jdk17 AS build

WORKDIR /app

# Copy everything
COPY . .

# Build jar
RUN gradle clean bootJar -x test

# ---- Run stage ----
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]