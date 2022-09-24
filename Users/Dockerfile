FROM openjdk:18-oracle

WORKDIR /app

COPY Common Common

RUN cd Common && ./mvnw clean install compile package

COPY Users/pom.xml Users/pom.xml
COPY Users/mvnw Users/mvnw
COPY Users/mvnw.cmd Users/mvnw.cmd
COPY Users/.mvn Users/.mvn

WORKDIR /app/Users

RUN ./mvnw install -Dmaven.main.skip -Dmaven.test.skip
COPY Users .

CMD ["./mvnw", "spring-boot:run"]
