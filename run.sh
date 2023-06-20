#!/bin/sh

mvn clean package -DskipTests

sleep 2

docker-compose up -d

sleep 5

cd gateway
java -jar gateway-1.0.jar &

cd ..

cd data-publisher
java -jar data-publisher-1.0.jar &

cd ..

cd data-projector
java -jar data-projector-1.0.jar &

cd ..

cd query-service
java -jar query-service-1.0.jar &

cd ..

sleep 3

cd frontend

ng serve