# Exercice 09 — API CRUD Dogs + API Logs

```
docker compose up -d --build
```

* `crud-api` (port 8080) : CRUD sur `/api/v1/dogs`, envoie un log à chaque appel.
* `logs-api` (port 8081) : `GET /api/v1/logs`, `POST /api/v1/logs`.

```
curl -X POST localhost:8080/api/v1/dogs -H "Content-Type: application/json" -d "{\"name\":\"Rex\",\"breed\":\"Labrador\",\"age\":3}"
curl localhost:8081/api/v1/logs
```
