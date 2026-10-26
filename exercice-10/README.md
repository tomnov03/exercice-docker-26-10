# Exercice 10 — Notes App (Spring Boot + PostgreSQL)

```
docker compose up -d --build
docker compose ps
```

Test variables d'environnement :
```
curl http://localhost:8080/api/info/env
```

Test persistance :
```
curl -X POST http://localhost:8080/api/notes -H "Content-Type: application/json" -d "{\"title\":\"Test persistance\",\"content\":\"test\"}"
docker compose stop
docker compose up -d
curl http://localhost:8080/api/notes
```

Test perte des données (suppression des volumes) :
```
docker compose down -v
docker compose up -d
curl http://localhost:8080/api/notes
```

Test modification de variable :
```
sed -i '' 's/APP_NAME=.*/APP_NAME=Mon Application Modifiee/' .env
docker compose up -d --force-recreate app
curl http://localhost:8080/api/info/app
```
