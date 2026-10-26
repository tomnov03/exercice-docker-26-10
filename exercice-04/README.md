# Exercice 04 — Deux conteneurs communiquant par leur nom

```
docker build -t ubuntu-ping .
docker network create net-exo4
docker run -d --name conteneur-a --network net-exo4 ubuntu-ping
docker run -d --name conteneur-b --network net-exo4 ubuntu-ping
docker exec conteneur-a ping -c 3 conteneur-b
docker exec conteneur-b ping -c 3 conteneur-a
```
