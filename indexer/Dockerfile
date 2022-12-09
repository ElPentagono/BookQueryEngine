FROM eclipse-temurin:11-jre-alpine
COPY target/dependency-jars /appI/dependency-jars
ADD target/indexer.jar /appI/indexer.jar
ENTRYPOINT java -jar appI/indexer.jar