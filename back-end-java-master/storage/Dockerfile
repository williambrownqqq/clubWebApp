FROM amazoncorretto:11-alpine-jdk

# Refer to Maven build -> finalName
ARG JAR_FILE=./target/storage-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# create folder for stored files
RUN mkdir -p stored-files

# cp target/{jarfileName}.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]