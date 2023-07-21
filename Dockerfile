FROM openjdk:11
VOLUME /tmp
COPY ./target/*.jar sisfin-maintenance.jar
ENTRYPOINT ["java","-jar","/sisfin-maintenance.jar"]