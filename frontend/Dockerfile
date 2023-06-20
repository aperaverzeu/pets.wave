FROM node:16.8.1 as build

WORKDIR /usr/local/app

COPY ./ /usr/local/app/

RUN yarn install

RUN yarn run build

FROM nginx:latest

COPY --from=build /usr/local/app/dist/sample-angular-app /usr/share/nginx/html

EXPOSE 80
