FROM eclipse-temurin:11-jre-alpine
COPY target/dependency-jars /app/dependency-jars
ADD target/crawler.jar /app/crawler.jar
ENTRYPOINT java -jar app/crawler.jar