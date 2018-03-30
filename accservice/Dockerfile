FROM java:8-jre
MAINTAINER Alexey Smirnov <aleksey.smirnov89@gmail.com>

RUN
ADD ./rest-service/target/accservice.jar /app/
CMD ["java", "-Xmx256m", "-jar", "/app/accservice.jar"]

EXPOSE 8443