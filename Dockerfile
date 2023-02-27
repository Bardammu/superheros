FROM bellsoft/liberica-openjdk-alpine:17.0.6-x86_64 as build
WORKDIR /workspace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./
RUN mkdir -p extracted && (java -Djarmode=layertools -jar *.jar extract --destination extracted)

FROM bellsoft/liberica-openjdk-alpine:17.0.6-x86_64
ARG EXTRACTED=/workspace/extracted
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
#COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]