FROM alpine:latest
RUN apk update
RUN apk add openjdk11-jre
COPY webservice-1.0-SNAPSHOT-jar-with-dependencies.jar /appW/webservice-1.0-SNAPSHOT-jar-with-dependencies.jar
CMD java -jar /appW/webservice-1.0-SNAPSHOT-jar-with-dependencies.jar