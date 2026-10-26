# Exercice 01 — Image ubuntu-nginx

## Étapes manuelles (conteneur + commit)

```
docker run -it --name ubuntu-nginx-build ubuntu:24.04 bash
apt-get update
apt-get install -y nginx
exit
docker commit ubuntu-nginx-build ubuntu-nginx
```

## Équivalent reproductible (Dockerfile)

```
docker build -t ubuntu-nginx .
docker run -d -p 8080:80 ubuntu-nginx
```

Vérification : http://localhost:8080
