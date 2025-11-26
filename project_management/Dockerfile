# ===== Stage 1: Build =====
# Use Maven + JDK 18 image to build the Spring Boot app
FROM maven:3.9.11-eclipse-temurin-17 AS builder


# Set working directory inside the container
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache
COPY pom.xml .

# Download dependencies (cached if pom.xml doesn't change)
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the project, skip tests
RUN mvn clean package -DskipTests

# ===== Stage 2: Run =====
# Use a lightweight JDK 18 image to run the app
FROM maven:3.9.11-eclipse-temurin-17

# Set working directory inside the container
WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port (Spring Boot default)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]