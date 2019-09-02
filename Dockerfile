FROM java:8
EXPOSE 8080
ADD /target/WebappTest-1.0-SNAPSHOT.jar WebappTest-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","WebappTest-1.0-SNAPSHOT.jar"]