FROM openjdk:18-bullseye

WORKDIR /app

RUN apt update && apt upgrade -y
RUN apt install wget -y
RUN wget https://archive.apache.org/dist/kafka/3.0.0/kafka_2.13-3.0.0.tgz
RUN tar xzf kafka_2.13-3.0.0.tgz
RUN rm kafka_2.13-3.0.0.tgz
RUN touch run.sh
RUN echo "#!/bin/bash" >> ./run.sh
RUN echo "./kafka_2.13-3.0.0/bin/zookeeper-server-start.sh ./kafka_2.13-3.0.0/config/zookeeper.properties & sleep 10;./kafka_2.13-3.0.0/bin/kafka-server-start.sh ./kafka_2.13-3.0.0/config/server.properties" >> run.sh
RUN chmod +x ./run.sh

CMD ["./run.sh"]


