FROM maven:latest

COPY . /itembase/src/app
WORKDIR /itembase/src/app

RUN mvn clean package

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/usg-1.0.jar"]