FROM openjdk:18-oracle

WORKDIR /app/Common
COPY Common .

RUN ./mvnw clean package

WORKDIR /app/Users
COPY Users .

CMD ["./mvnw", "spring-boot:run"]
