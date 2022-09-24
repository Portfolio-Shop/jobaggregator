FROM openjdk:18-oracle

WORKDIR /app

COPY Common Common

RUN cd Common && ./mvnw clean install compile package

COPY Scraper/pom.xml Scraper/pom.xml
COPY Scraper/mvnw Scraper/mvnw
COPY Scraper/mvnw.cmd Scraper/mvnw.cmd
COPY Scraper/.mvn Scraper/.mvn

WORKDIR /app/Scraper

RUN ./mvnw install
COPY Scraper .

CMD ["./mvnw", "spring-boot:run"]

