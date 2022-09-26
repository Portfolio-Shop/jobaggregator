FROM node:16-alpine

WORKDIR /app

COPY package.json .

RUN yarn

COPY . .

CMD ["npm","run", "dev"]