# --- Etapa 1: Compilación (Build) ---
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Cachear dependencias descargando el pom offline
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente del microservicio de logística
COPY src src

# Compilar omitiendo tests para acelerar los tiempos en Render
RUN mvn clean package -DskipTests

# --- Etapa 2: Imagen de producción liviana (Runtime) ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el ejecutable final .jar desde el contenedor de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto del microservicio
EXPOSE 8081

# Lanzamiento
ENTRYPOINT ["java", "-jar", "app.jar"]
