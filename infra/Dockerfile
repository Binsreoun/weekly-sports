FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS builder
ARG JAR_FILE=build/libs/weekly-sports.jar
COPY ${JAR_FILE} weekly-sports.jar
ENTRYPOINT ["java","-jar","/weekly-sports.jar"]
