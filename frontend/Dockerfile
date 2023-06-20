FROM node:16.18.1 as build

RUN mkdir /project
WORKDIR /project

RUN npm install -g @angular/cli@16

COPY package.json yarn.lock ./
RUN yarn install --frozen-lockfile

COPY . .
CMD ["ng", "serve", "--host", "0.0.0.0"]
