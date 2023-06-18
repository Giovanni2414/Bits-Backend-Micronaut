# Establecer la imagen base
FROM maven:3.8.4-openjdk-11-slim AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml
COPY pom.xml .

# Descargar las dependencias de Maven
RUN mvn dependency:go-offline -B

# Copiar el código fuente de la aplicación
COPY src ./src

# Compilar la aplicación
RUN mvn package -DskipTests

# Establecer una nueva imagen base para la aplicación
FROM openjdk:11-jre-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el artefacto construido desde la etapa anterior
COPY --from=build /app/target/BitsBackend.jar ./app.jar

# Exponer el puerto en el que la aplicación va a escuchar
EXPOSE 8081

# Establecer el comando de inicio de la aplicación
CMD ["java", "-jar", "app.jar"]