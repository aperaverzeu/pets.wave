FROM amazoncorretto:17.0.6-alpine3.17
COPY target/query-service-1.0.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app.jar"]
