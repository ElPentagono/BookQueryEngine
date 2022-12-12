FROM eclipse-temurin:11-jre-alpine
COPY target/dependency-jars /appW/dependency-jars
ADD target/webservice.jar /appW/webservice.jar
ENTRYPOINT java -jar appW/webservice.jar