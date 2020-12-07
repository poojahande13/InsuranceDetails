FROM openjdk:8u181-jre-alpine3.8

ADD target /spicejetfileparser/target

WORKDIR /spicejetfileparser/target

EXPOSE 80

CMD java $JAVA_OPTS -cp lib/*:spicejetfileparser-1.0-SNAPSHOT.jar com.Xangars.MapperEngine
