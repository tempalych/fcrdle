FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY target/*.jar app.jar
COPY src/main/resources/Stadiums.csv src/main/resources/Stadiums.csv
ENTRYPOINT ["java","-jar","/app.jar"]