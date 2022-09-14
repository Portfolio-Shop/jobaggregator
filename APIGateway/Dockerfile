FROM openjdk:18-oracle

WORKDIR /app

COPY . .

RUN ./mvnw clean install package

CMD ["java", "-jar", "target/APIGateway-0.0.1-SNAPSHOT.jar"]