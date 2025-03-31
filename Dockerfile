FROM openjdk:17

COPY build/libs/neti_back-0.0.1-SNAPSHOT.jar /build/libs/neti_back-0.0.1-SNAPSHOT.jar
COPY /docker/application.yml /application.yml

EXPOSE 8081

ENTRYPOINT ["java", " -Dfile.encoding=UTF-8 -Xms64m -Xmx256m", "-jar", "build/libs/neti_back-0.0.1-SNAPSHOT.jar", "--spring.config.location=./application.yml"]