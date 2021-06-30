#FROM openjdk:8-jdk-alpine
#ADD target/spring-boot.jar spring-boot.jar
#EXPOSE 8085
#ENTRYPOINT ["java", "-jar", "spring-boot.jar"]

FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ADD target/${JAR_FILE} spring-boot.jar
ENTRYPOINT ["java","-jar","/spring-boot.jar"]