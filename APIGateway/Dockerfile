FROM openjdk:18-oracle

WORKDIR /app

COPY APIGateway/pom.xml APIGateway/pom.xml
COPY APIGateway/mvnw APIGateway/mvnw
COPY APIGateway/mvnw.cmd APIGateway/mvnw.cmd
COPY APIGateway/.mvn APIGateway/.mvn

WORKDIR /app/APIGateway

RUN ./mvnw install -Dmaven.main.skip -Dmaven.test.skip
COPY APIGateway .

CMD ["./mvnw", "spring-boot:run"]
