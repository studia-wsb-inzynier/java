# Użyj obrazu openjdk jako podstawy
FROM maven:3.8.5-openjdk-17 AS build

# Ustaw katalog roboczy
WORKDIR /app

# Skopiuj plik pom.xml i pobierz zależności Mavena (warstwa cache dla zależności)
COPY pom.xml ./
RUN mvn dependency:go-offline

# Skopiuj cały kod źródłowy projektu do kontenera
COPY src ./src

# Buduj aplikację i twórz plik JAR
RUN mvn clean package -DskipTests

# Użyj oddzielnego obrazu openjdk dla uruchomienia aplikacji
FROM openjdk:17-jdk-alpine

# Ustaw katalog roboczy dla aplikacji
WORKDIR /app

# Skopiuj wygenerowany plik JAR z poprzedniego etapu budowy
COPY --from=build /app/target/e-tutor-0.0.1-SNAPSHOT.jar /app/e-tutor.jar

# Ustawienie komendy startowej
ENTRYPOINT ["java", "-jar", "/app/e-tutor.jar"]
