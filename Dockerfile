FROM amazoncorretto:21-alpine AS builder

LABEL authors="blackshadow"

WORKDIR /app

ARG JAR_FILE=target/catalog-service.jar 

COPY ${JAR_FILE} catalog-service.jar

RUN java -Djarmode=layertools -jar catalog-service.jar extract 

#====================================================================================================

FROM amazoncorretto:21-alpine

WORKDIR /app

RUN addgroup -S springgroup && adduser -S springuser -G springgroup

USER springuser

COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]


