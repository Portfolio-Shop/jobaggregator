FROM node:16-alpine

WORKDIR /app

COPY . .

RUN yarn install

RUN npm run build

CMD ["npm","start"]