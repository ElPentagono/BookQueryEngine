FROM alpine:latest
RUN apk update
RUN apk add openjdk11-jre
COPY indexer-1.0-SNAPSHOT-jar-with-dependencies.jar /appI/indexer-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY src/main/resources/stopwords.txt /appI/stopwords.txt
CMD ["java", "-jar", "/appI/indexer-1.0-SNAPSHOT-jar-with-dependencies.jar"]