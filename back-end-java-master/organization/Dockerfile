FROM amazoncorretto:11-alpine-jdk

# Refer to Maven build -> finalName
ARG JAR_FILE=./target/organization-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/{jarfileName}.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]