# 📚 e-Tutor

## 🎓 Projekt inżynierski
Aplikacja **e-Tutor** jest tworzona w ramach mojej pracy inżynierskiej.  
Jej celem jest wsparcie korepetytorów w zarządzaniu uczniami oraz organizacji zajęć.

## ✨ Funkcjonalności
- 👨‍🏫 **Zarządzanie uczniami** – korepetytor może wygenerować unikalny kod i przekazać go uczniowi, aby ten dołączył do jego listy kontaktów.  
- 📅 **Kalendarz zajęć** – nauczyciel może planować lekcje, a system automatycznie sprawdza, czy termin nie jest już zajęty.  
- 📝 **Quizy** – możliwość tworzenia quizów i udostępniania ich uczniom.  


## Uruchamianie aplikacji

Aplikacja działa w trybie: **dev** (baza H2, konsola pod `/h2-console`).

### Dev
```sh
docker-compose -f docker-compose.dev.yaml build
docker-compose -f docker-compose.dev.yaml up
```

## Dokumentacja API
Dostępna pod: `/docs`

