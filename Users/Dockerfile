FROM openjdk:18-oracle

WORKDIR /app

COPY . .

RUN cd Common && ./mvnw clean install compile package
RUN cd Users && ./mvnw clean install 

CMD ["cd", "Users", "&&", "./mvnw", "spring-boot:run"]
