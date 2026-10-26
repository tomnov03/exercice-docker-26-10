# Exercice 05 — MySQL + Adminer

```
docker compose up -d
```

Vérification : http://localhost:8080 (serveur: `mysql`, utilisateur: `exo5user`, mot de passe: `exo5pass`, base: `exo5db`)

Persistance :
```
docker compose rm -sf mysql
docker compose up -d mysql
```
Les données restent car elles sont stockées dans le volume nommé `mysql-data`.
