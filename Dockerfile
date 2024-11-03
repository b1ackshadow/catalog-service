#FROM maven:3.9.9-amazoncorretto-17-alpine AS MAVEN_BUILD
#
#COPY pom.xml /build/
#COPY src /build/src/
#WORKDIR /build/
#
#RUN mvn clean package
#=====================================================================================
FROM amazoncorretto:21-alpine

LABEL authors="blackshadow"

WORKDIR /app

#COPY --from=MAVEN_BUILD /build/target/catalog-service.jar /app/
COPY target/catalog-service.jar /app

ENTRYPOINT ["java","-jar","catalog-service.jar"]

