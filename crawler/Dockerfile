FROM alpine:latest
RUN apk update
RUN apk add openjdk11-jre
COPY crawler-1.0-SNAPSHOT-jar-with-dependencies.jar /app/crawler-1.0-SNAPSHOT-jar-with-dependencies.jar
CMD ["java", "-jar", "/app/crawler-1.0-SNAPSHOT-jar-with-dependencies.jar"]