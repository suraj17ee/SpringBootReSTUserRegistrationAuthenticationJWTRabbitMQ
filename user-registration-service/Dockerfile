FROM openjdk
WORKDIR usr/lib
EXPOSE 9001
ADD ./target/user-registration-service-0.0.1-SNAPSHOT.jar /usr/lib/user-registration-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","user-registration-service-0.0.1-SNAPSHOT.jar"]