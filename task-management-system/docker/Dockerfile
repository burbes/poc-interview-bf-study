# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Download all required dependencies into one layer
RUN mvn dependency:go-offline -B
COPY src ./src
# Build the application
RUN mvn package -DskipTests
# Extract the JAR contents to improve layer caching
RUN mkdir -p target/extracted && \
    java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Add a non-root user to run the application
RUN addgroup --system --gid 1001 appuser && \
    adduser --system --uid 1001 --ingroup appuser appuser && \
    mkdir -p /app/logs && \
    chown -R appuser:appuser /app

# Set environment variables
ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:+UseG1GC"
ENV SPRING_PROFILES_ACTIVE="prod"

# Copy the layered JAR from the build stage
COPY --from=build --chown=appuser:appuser /app/target/extracted/dependencies/ ./
COPY --from=build --chown=appuser:appuser /app/target/extracted/spring-boot-loader/ ./
COPY --from=build --chown=appuser:appuser /app/target/extracted/snapshot-dependencies/ ./
COPY --from=build --chown=appuser:appuser /app/target/extracted/application/ ./

# Expose the application port
EXPOSE 8080

# Switch to non-root user
USER appuser

# Run the application
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

