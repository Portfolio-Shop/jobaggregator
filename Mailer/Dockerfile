FROM openjdk:18-oracle

WORKDIR /app

COPY Common Common

RUN cd Common && ./mvnw clean install compile package

COPY Mailer/pom.xml Mailer/pom.xml
COPY Mailer/mvnw Mailer/mvnw
COPY Mailer/mvnw.cmd Mailer/mvnw.cmd
COPY Mailer/.mvn Mailer/.mvn

WORKDIR /app/Mailer

RUN ./mvnw install
COPY Mailer .

CMD ["./mvnw", "spring-boot:run"]

