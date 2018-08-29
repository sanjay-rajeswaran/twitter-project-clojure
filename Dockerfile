FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/twitter-port.jar /twitter-port/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/twitter-port/app.jar"]
