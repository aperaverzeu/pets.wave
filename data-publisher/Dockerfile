FROM eclipse-temurin:17-jre-alpine
COPY target/data-publisher-1.0.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "/app.jar"]
