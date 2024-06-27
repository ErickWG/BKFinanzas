FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/BKFinanzas-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} BKFinanzas.jar
ENTRYPOINT ["java","-jar","/gym-partner.jar"]

