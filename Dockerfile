# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de tu proyecto al contenedor
COPY target/*.jar app.jar

# Expone el puerto en el que tu aplicación Spring Boot escuchará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
