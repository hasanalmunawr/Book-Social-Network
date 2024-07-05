# Build Stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime Stage
FROM  amazoncorretto:17
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

# Define few things
WORKDIR /app

EXPOSE 8088

ENV DB_URL=Jdbc:postgresql://postgres-sql-bsn:5432/book_social_network
ENV JAR_VERSION=${APP_VERSION}
ENV ACTIVE_PROFILE=${APP_VERSION}
ENV EMAIL_HOST_NAME=missing_host_name
ENV EMAIL_USER_NAME=missing_user_name
ENV EMAIL_PASSWORD=missing_password

COPY --from=build /build/target/book-network-*.jar /app/

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} book-network-${JAR_VERSION}.jar