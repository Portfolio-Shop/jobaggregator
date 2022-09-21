FROM openjdk:18-oracle

WORKDIR /app

COPY Common Common

RUN cd Common && ./mvnw clean install compile package

COPY Users Users

WORKDIR /app/Users

CMD ["./mvnw", "spring-boot:run"]
