# ── Build stage ──
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon || true
COPY src/ src/
RUN ./gradlew build --no-daemon -x test

# ── Deploy stage ──
FROM eclipse-temurin:21-jre-jammy

ENV TZ=America/Lima
ENV JAR_NAME=email-api-0.0.1-SNAPSHOT.jar
WORKDIR /app

# Copiamos el JAR compilado desde la etapa de construcción
COPY --from=builder /app/build/libs/$JAR_NAME ./app.jar

# Afinación JVM para contenedor
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:+UseG1GC -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

ENTRYPOINT ["java", "-jar", "app.jar"]