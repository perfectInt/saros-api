FROM openjdk:19 as build
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src
RUN chmod +x mvnw
RUN ./mvnw clean package

FROM openjdk:19
ARG JAR_FILE=target/news-service-0.0.1-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} .
EXPOSE 8080
ENTRYPOINT ["java","-jar","news-service-0.0.1-SNAPSHOT.jar"]