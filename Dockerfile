# --- Etapa 1: Compilación (Build) ---
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Cachear dependencias de Maven para acelerar compilaciones futuras
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente del microservicio de incentivos
COPY src src

# Compilar y empaquetar el proyecto omitiendo los tests
RUN mvn clean package -DskipTests

# --- Etapa 2: Imagen de ejecución liviana (Runtime) ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render asignará y leerá el puerto automáticamente mediante la variable PORT
EXPOSE 8082

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
