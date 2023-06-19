# Getting Started

## Local Development
### Prerequisite
- [JDK 11+](https://www.oracle.com/java/technologies/downloads/archive/)
- [Docker Engine](https://docs.docker.com/engine/install/)
- [Docker Compose](https://docs.docker.com/compose/install/)

**Note:** Installing [Docker Desktop ](https://docs.docker.com/get-docker/) is a preferable option since it includes 
Docker Engine and Docker Compose and also provides GUI for image management.

### TL;DR
To build and run cluster execute the following commands from project root directory:
```shell
./mvnw clean install
export SMTP_USERNAME=<your_gmail_address>
export SMTP_PASSWORD=<gmail_password_you_generated>
docker compose up -d
```
**NOTE:** Suggestions on how to get SMTP_USERNAME and SMTP_PASSWORD values are provided in [Set up environment](#smtp)
section of this file.
### Build

#### Build with docker-maven-plugin
The command below will generate jar files for all the services and build Docker images using [docker-maven-plugin](https://github.com/fabric8io/docker-maven-plugin).
```shell
./mvnw clean install
```
To skip tests execution you may use command below:
```shell
./mvnw clean install -DskipTests=true
```
#### Build without docker-maven-plugin
If you have any issues during docker-maven-plugin phase you can disable the plugin with the option below:
```shell
./mvnw clean install -Ddocker.skip=true
```
To disable the plugin and skip tests use the following command:
```shell
./mvnw clean install -DskipTests=true -Ddocker.skip=true
```
You can permanently disable docker plugin by going to [root pom file](pom.xml) and setting `docker-plugin.skip` property
to `true`.<br><br>
**NOTE:** If you will not use docker-maven-plugin then docker images will not be built. In such case make sure 
you start cluster with --build option every time you want to update images with the latest built jar file. Example command is shown
in the [Start & stop section](#start--stop) of this file.
### Set up environment
#### SMTP
Before running your application you would need configure SMTP server connection. In order to achieve this - the following 
environment variables are used by [mail-service configuration file](./mail/src/main/resources/application.yaml):
```yaml
mail:
    host: ${SMTP_HOST:smtp.gmail.com}
    port: ${SMTP_PORT:587}
    username: ${SMTP_USERNAME:}
    password: ${SMTP_PASSWORD:}
```
For development purposes the fastest way to set up SMTP server and generate credential might be Gmail SMTP server, 
to do so you can follow [these instructions](https://www.geeksforgeeks.org/spring-boot-sending-email-via-smtp/).<br>
When you generated your password run commands bellow before starting cluster.
```shell
export SMTP_USERNAME=<your_gmail_address>
export SMTP_PASSWORD=<gmail_password_you_generated>
```
You can also set SMTP_USERNAME and SMTP_PASSWORD values in [.env](.env) file to avoid exporting variables on every run in
a new shell session. **But make sure to never commit and push any sensitive data!**<br>

**NOTE:** If you use a different SMTP server than Gmail - you will also need to set SMTP_HOST and SMTP_PORT values.
#### Volumes
[Storage service](storage) stores files on file system of its container. To persist that data between runs a [volume](https://docs.docker.com/storage/volumes/) 
is used and bound to folder in project root - [stored-files](stored-files). To change this path you would need to
override STORED_FILES_MOUNT_PATH environment variable.

#### Useful reading
To get more information on docker-compose environment management you can use [the following doc](https://docs.docker.com/compose/environment-variables/).
### Start & stop

After docker images were built, in the previous step, execute command below form project root folder to start the cluster:
```shell
docker compose up -d
```
To rebuild docker images using the currently assembled jar files:
```shell
docker compose up --build -d
```
To stop cluster execute command below form project root folder:
```shell
docker compose down
```
