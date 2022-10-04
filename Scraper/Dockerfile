FROM openjdk:18-oracle

WORKDIR /drivers
RUN curl -L https://github.com/mozilla/geckodriver/releases/download/v0.31.0/geckodriver-v0.31.0-linux32.tar.gz -o gecko.tar.gz
RUN tar -xf gecko.tar.gz

WORKDIR /app/Common
COPY Common .

RUN ./mvnw clean package

WORKDIR /app/Scraper
COPY Scraper .
RUN mvnw clean package

CMD ["./mvnw", "spring-boot:run"]
