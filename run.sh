#!/bin/sh

mvn clean package -d skipTests

sleep 2

docker-compose up -d

sleep 5

cd gateway
nohup java -jar gateway-1.0.jar &

cd ..

cd data-publisher
nohup java -jar data-publisher-1.0.jar &

cd ..

cd data-projector
nohup java -jar data-projector-1.0.jar &

cd ..

cd query-service
nohup java -jar query-service-1.0.jar &

cd ..

sleep 3

cd frontend

ng serve