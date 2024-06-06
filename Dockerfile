FROM openjdk:19-jdk

COPY target/blog-backend.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar" ,  "blog-backend.jar"]