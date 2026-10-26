# Secrets

Ce dossier contient les fichiers de secrets utilisés par Docker Compose (montés en `/run/secrets/...` dans les conteneurs). Ils ne sont jamais versionnés (voir `.gitignore`).

Fichiers à créer avant le premier démarrage :

```
echo "un-mot-de-passe-root-solide" > secrets/db_root_password.txt
echo "un-mot-de-passe-utilisateur-solide" > secrets/db_password.txt
```
