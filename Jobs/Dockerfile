FROM openjdk:18-oracle

WORKDIR /app

COPY Common Common

RUN cd Common && ./mvnw clean install compile package

COPY Jobs Jobs

RUN cd Jobs && ./mvnw clean install 

CMD ["cd", "Jobs", "&&", "./mvnw", "spring-boot:run"]
