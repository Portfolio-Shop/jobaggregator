FROM openjdk:18-oracle

WORKDIR /app

COPY APIGateway APIGateway

WORKDIR /app/APIGateway

RUN ./mvnw install

CMD ["./mvnw", "spring-boot:run"]
