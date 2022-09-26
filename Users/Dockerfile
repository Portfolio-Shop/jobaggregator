FROM openjdk:18-oracle

WORKDIR /app/Common
COPY Common .

RUN ./mvnw clean package

WORKDIR /app/Users
COPY Users .

RUN ./mvnw clean package

CMD ["./mvnw", "spring-boot:run"]
