# ─────────────────────────────────────────────────
# Stage 1: Build
# ─────────────────────────────────────────────────
FROM maven:3.9.6-amazoncorretto-21 AS builder

WORKDIR /app
COPY pom.xml .
# Download dependencies first (Docker layer cache optimization)
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# ─────────────────────────────────────────────────
# Stage 2: Runtime (slim JRE image)
# ─────────────────────────────────────────────────
FROM amazoncorretto:21-alpine

WORKDIR /app

# Create non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy the jar
COPY --from=builder /app/target/*.jar app.jar

# Set ownership
RUN chown appuser:appgroup app.jar

USER appuser

# ECS Fargate health check port
EXPOSE 8081

# Pass active Spring profile via env variable (overridden in ECS Task Definition)
ENV SPRING_PROFILES_ACTIVE=dev

ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
