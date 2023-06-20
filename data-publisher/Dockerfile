FROM amazoncorretto:17.0.6-alpine3.17
COPY target/data-publisher-1.0.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "/app.jar"]
