FROM amazoncorretto:17.0.6-alpine3.17
COPY target/discovery-service-1.0.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]
