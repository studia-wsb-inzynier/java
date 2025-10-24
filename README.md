# 📚 e-Tutor

## 🎓 Projekt inżynierski
Aplikacja **e-Tutor** jest tworzona w ramach mojej pracy inżynierskiej.  
Jej celem jest wsparcie korepetytorów w zarządzaniu uczniami oraz organizacji zajęć.

## ✨ Funkcjonalności
- 👨‍🏫 **Zarządzanie uczniami** – korepetytor może wygenerować unikalny kod i przekazać go uczniowi, aby ten dołączył do jego listy kontaktów.  
- 📅 **Kalendarz zajęć** – nauczyciel może planować lekcje, a system automatycznie sprawdza, czy termin nie jest już zajęty.  
- 📝 **Quizy** – możliwość tworzenia quizów i udostępniania ich uczniom.  


## Uruchamianie aplikacji

## Konfiguracja

### Plik `.env`

Przykładowy plik znajduje się w:  
.env.example

Skopiuj go jako `.env` i uzupełnij swoje dane.  
Przykład:
```env
# ========================
# Database configuration
# ========================
# Used only in 'prod' profile
DATABASE_URL=jdbc:postgresql://HOST:PORT/DB_NAME
DATABASE_USERNAME=your_db_username
DATABASE_PASSWORD=your_db_password

# ========================
# Email configuration
# ========================
# Used in 'prod' and 'dev' profiles
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=your_email@gmail.com
EMAIL_PASSWORD=your_email_password
```

- **dev**
    - Baza: H2 (w pamięci)
    - Konsola H2 dostępna pod: [`/h2-console`](http://localhost:8080/h2-console)
- **prod**
    - Baza: PostgreSQL
    - Konfiguracja bazy i danych logowania w pliku `.env`

### Dev
```sh
docker compose -f docker-compose.dev.yaml build
docker compose -f docker-compose.dev.yaml up
```

### Prod
```sh
docker-compose -f docker-compose.prod.yaml build
docker-compose -f docker-compose.prod.yaml up
```

## Dokumentacja API
Dostępna pod: `/docs`

