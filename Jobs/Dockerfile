FROM openjdk:18-oracle

WORKDIR /app

COPY . .

RUN cd Common && ./mvnw clean install compile package
RUN cd Jobs && ./mvnw clean install 

CMD ["cd", "Jobs", "&&", "./mvnw", "spring-boot:run"]
