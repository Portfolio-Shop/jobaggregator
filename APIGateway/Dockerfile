FROM openjdk:18-oracle

WORKDIR /app/APIGateway
COPY APIGateway .

RUN ./mvnw clean package

CMD ["./mvnw", "spring-boot:run"]
