FROM eclipse-temurin:11-jdk
VOLUME /tmp
COPY ./target/*.jar sisfin-maintenance.jar
ENTRYPOINT ["java","-jar","/sisfin-maintenance.jar"]