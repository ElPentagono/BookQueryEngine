FROM eclipse-temurin:11-jre-alpine
COPY target/dependency-jars /metadataDatamart/dependency-jars
ADD target/metadata.jar /metadataDatamart/metadata.jar
ENTRYPOINT java -jar /metadataDatamart/metadata.jar