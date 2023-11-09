# Use the official OpenJDK 17 base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper files and build.gradle for dependencies
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .

# Copy the application source code
COPY src src

# Build the application using Gradle
RUN ./gradlew build

# Copy the JAR file from the build directory to the working directory
COPY build/libs/blogapp-0.0.1-SNAPSHOT.jar /app/blogapp.jar

# Expose the port that your Spring Boot application will run on
EXPOSE 8855

# Specify the command to run your application
CMD ["java", "-jar", "blogapp.jar"]
