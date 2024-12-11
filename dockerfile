# Use a base image with Java installed
FROM openjdk:22-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/library-management-1.0.0-SNAPSHOT.jar /app/library-management.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "library-management.jar"]
