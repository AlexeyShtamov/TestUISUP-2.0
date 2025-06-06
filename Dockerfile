FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY .mvn/ .mvn
COPY --chmod=755 mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests
CMD ["java", "-jar", "target/uisupTest-0.0.1-SNAPSHOT.jar"]