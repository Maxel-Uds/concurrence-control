FROM amazoncorretto:17-alpine3.19

COPY build/libs/concurrence-control-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_PROFILES_ACTIVE develop

CMD $JAVA_HOME/bin/java -Dreactor.netty.pool.maxIdleTime=60 -Dreactor.netty.pool.maxLifeTime=300 -Dreactor.netty.pool.leasingStrategy=lifo -Dreactor.netty.http.server.accessLogEnabled=true -jar app.jar