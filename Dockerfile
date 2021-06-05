FROM openjdk:11
WORKDIR app

COPY ./target/proposta.jar .
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "proposta.jar"]
