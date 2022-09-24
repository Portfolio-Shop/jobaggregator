FROM openjdk:18-oracle

WORKDIR /app

COPY Common Common

RUN cd Common && ./mvnw clean install compile package

COPY Mailer Mailer

WORKDIR /app/Mailer

RUN ./mvnw install

CMD ["./mvnw", "spring-boot:run"]

