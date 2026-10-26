# Exercice 08 — API CRUD Dogs (Spring Boot + MySQL)

```
docker compose up -d --build
```

Endpoints :
* `GET /api/v1/dogs`
* `GET /api/v1/dogs/{dogId}`
* `POST /api/v1/dogs`
* `PUT /api/v1/dogs/{dogId}`
* `DELETE /api/v1/dogs/{dogId}`

```
curl -X POST localhost:8080/api/v1/dogs -H "Content-Type: application/json" -d "{\"name\":\"Rex\",\"breed\":\"Labrador\",\"age\":3}"
curl localhost:8080/api/v1/dogs
```
