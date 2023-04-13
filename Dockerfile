FROM openjdk:11
VOLUME /tmp
ADD ./target/*.jar sisfin-maintenance.jar
ENTRYPOINT ["java","-jar","/sisfin-maintenance.jar"]