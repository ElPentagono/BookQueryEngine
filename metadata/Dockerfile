FROM alpine:latest
RUN apk update
RUN apk add openjdk11-jre
COPY metadata-1.0-SNAPSHOT-jar-with-dependencies.jar /appM/metadata-1.0-SNAPSHOT-jar-with-dependencies.jar
CMD ["java", "-jar", "/appM/metadata-1.0-SNAPSHOT-jar-with-dependencies.jar"]