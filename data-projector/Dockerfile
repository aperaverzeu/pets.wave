FROM amazoncorretto:17.0.6-alpine3.17
COPY target/data-projector-1.0.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/app.jar"]
