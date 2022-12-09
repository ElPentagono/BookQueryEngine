FROM eclipse-temurin:11-jre-alpine
COPY target/dependency-jars /appM/dependency-jars
ADD target/metadata.jar /appM/metadata.jar
ENTRYPOINT java -jar appM/metadata.jar