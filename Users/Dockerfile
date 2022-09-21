FROM openjdk:18-oracle

WORKDIR /app

COPY Common Common

RUN cd Common && ./mvnw clean install compile package

COPY Users Users

RUN cd Users && ./mvnw clean install 

CMD ["cd", "Users", "&&", "./mvnw", "spring-boot:run"]
