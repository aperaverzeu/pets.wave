FROM amazoncorretto:17.0.6-alpine3.17
COPY target/gateway-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
