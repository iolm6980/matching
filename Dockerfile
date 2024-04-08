FROM openjdk:17

WORKDIR /app
VOLUME /var/jenkins_home/workspace/match
COPY /build/libs/matching-0.0.1-SNAPSHOT.jar app.jar
COPY Profanity.txt Profanity.txt

EXPOSE 8090

CMD ["java", "-jar", "app.jar"]
