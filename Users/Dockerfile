FROM openjdk:18-oracle

WORKDIR /app

COPY .. .

RUN cd Common && ./mvnw clean install package
RUN cd Users && ./mvnw clean install package

CMD ["java", "-jar", "Users/target/Users-0.0.1-SNAPSHOT.jar"]