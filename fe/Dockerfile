# Usar una imagen base de Maven para construir la aplicación
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto del código fuente del proyecto
COPY src ./src

# Compilar y empaquetar la aplicación
RUN mvn clean package

# Usar una imagen base más ligera para ejecutar la aplicación
FROM eclipse-temurin:17-jdk-alpine

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el WAR construido desde la etapa de construcción anterior
COPY --from=build /app/target/fe-0.0.1-SNAPSHOT.war .

# Exponer el puerto que utiliza el microservicio
EXPOSE 8084

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "fe-0.0.1-SNAPSHOT.war"]
