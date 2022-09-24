FROM openjdk:18-oracle

WORKDIR /app

COPY Common Common

RUN cd Common && ./mvnw clean install compile package

COPY Jobs Jobs

WORKDIR /app/Jobs

RUN ./mvnw install

CMD ["./mvnw", "spring-boot:run"]

