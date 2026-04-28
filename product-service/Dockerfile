# --- ETAPA 1 : BUILD (Contruccion) ---
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
#Mesa de trabajo, carpeta del contenedor
WORKDIR /app
#Prime punto copia nuestro codigo, y segundo puntp lo pega en el contenedor
COPY . .
#Contruir el jar sin la ejecucion de test
RUN mvn clean package -DskipTests

# --- ETAPA 2 : RUNTIME (Tiempo de ejecucion) ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
#Busca en la etapa de contruccion y no en mi pc
COPY --from=builder /app/target/*.jar product.jar
#Porque no se ura run, porque es en tiempo de contruccion y entrypoint e es etiempo de ejcucion
ENTRYPOINT ["java", "-jar", "product.jar"]