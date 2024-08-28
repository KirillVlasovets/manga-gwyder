FROM openjdk:21-jdk
WORKDIR /manga-chitalka
COPY ./build/libs/manga-gwyder-0.0.1-SNAPSHOT.jar /build/libs/manga-gwyder-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/build/libs/manga-gwyder-0.0.1-SNAPSHOT.jar"]
