FROM eclipse-temurin:11-jre-alpine
COPY target/dependency-jars /invertedIndexDatamart/dependency-jars
ADD target/indexer.jar /invertedIndexDatamart/indexer.jar
ENTRYPOINT java -jar /invertedIndexDatamart/indexer.jar