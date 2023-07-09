FROM openjdk:17
COPY target/stocks-data-0.0.1-SNAPSHOT.jar stocks-data-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/stocks-data-0.0.1-SNAPSHOT.jar"]