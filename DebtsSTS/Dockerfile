FROM openjdk:11
COPY target/DebtsSTS-0.0.1-SNAPSHOT.jar resource.jar
RUN bash -c "touch /resource.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","resource.jar"]
