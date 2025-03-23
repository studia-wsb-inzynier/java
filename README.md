# e-Tutor

## Uruchamianie aplikacji

Aplikacja działa w dwóch trybach: **dev** (baza H2, konsola pod `/h2-console`) i **prod** (PostgreSQL).

### Dev
```sh
docker-compose -f docker-compose.dev.yaml build
docker-compose -f docker-compose.dev.yaml up
```

### Prod
```sh
docker-compose -f docker-compose.prod.yaml build
docker-compose -f docker-compose.prod.yaml up
```

## Dokumentacja API
Dostępna pod: `/docs`

