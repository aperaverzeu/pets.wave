FROM node:16.18.1 as build

WORKDIR /usr/local/app

COPY package.json yarn.lock ./

RUN yarn install

COPY . .

RUN yarn run build

RUN ls -la dist

FROM nginx:latest

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build /dist/ /usr/share/nginx/html

EXPOSE 80
