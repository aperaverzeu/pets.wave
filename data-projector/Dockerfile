FROM eclipse-temurin:17-jre-alpine
COPY target/data-projector-1.0.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/app.jar"]
