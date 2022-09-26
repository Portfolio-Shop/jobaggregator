FROM openjdk:18-oracle

WORKDIR /app/Common
COPY Common .

RUN ./mvnw clean package

WORKDIR /app/Jobs
COPY Jobs .

RUN ./mvnw clean package

CMD ["./mvnw", "spring-boot:run"]
