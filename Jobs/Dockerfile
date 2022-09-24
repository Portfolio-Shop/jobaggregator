FROM openjdk:18-oracle

WORKDIR /app

COPY Common Common

RUN cd Common && ./mvnw clean install compile package

COPY Jobs/pom.xml Jobs/pom.xml
COPY Jobs/mvnw Jobs/mvnw
COPY Jobs/mvnw.cmd Jobs/mvnw.cmd
COPY Jobs/.mvn Jobs/.mvn

WORKDIR /app/Jobs

RUN ./mvnw install -Dmaven.main.skip -Dmaven.test.skip
COPY Jobs .

CMD ["./mvnw", "spring-boot:run"]

