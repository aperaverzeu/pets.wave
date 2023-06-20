FROM eclipse-temurin:17-jre-alpine
COPY target/query-service-1.0.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app.jar"]
