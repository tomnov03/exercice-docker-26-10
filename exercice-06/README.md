# Exercice 06 — Serveur NGINX de développement via volume

```
mkdir -p site
docker run -d --name dev-nginx -p 8080:80 -v "$(pwd)/site:/usr/share/nginx/html" nginx:1.27-alpine
```

Tout fichier ajouté dans `site/` est immédiatement servi sur http://localhost:8080, sans installer NGINX localement.
