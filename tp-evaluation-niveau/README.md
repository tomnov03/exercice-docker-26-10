# TP Docker — Infrastructure WordPress sécurisée

## Prérequis

```
cp .env.example .env
echo "mot-de-passe-root" > secrets/db_root_password.txt
echo "mot-de-passe-user" > secrets/db_password.txt
sh scripts/gen-cert.sh
```

## Démarrage

```
docker compose up -d --build
```

Accès : http://localhost:8080 (redirige vers https://localhost:8443, certificat auto-signé).

Démarrer en plus l'outil d'administration de base de données (bonus profils) :
```
docker compose --profile admin up -d
```
phpMyAdmin : http://localhost:8081

## Arrêt

```
docker compose down
```

## Vérifications

```
docker compose ps
docker compose logs -f
docker stats
```

Voir `docs/architecture.md` pour le détail des réseaux, des healthchecks et la procédure de diagnostic.

## Sauvegarde / restauration

```
sh scripts/backup.sh
sh scripts/restore.sh <timestamp-affiche-par-backup.sh>
```

Les sauvegardes (dump SQL + archive des fichiers WordPress) sont stockées dans `backups/` (non versionné).

## Sécurité

- Aucun mot de passe en clair dans `compose.yaml` : les identifiants MariaDB/WordPress passent par des secrets Docker Compose montés en fichiers (`*_FILE`), lus depuis `secrets/*.txt` (non versionnés, voir `secrets/README.md`).
- `mariadb` n'est connecté qu'au réseau interne `backend-net` : aucun port MySQL n'est publié sur l'hôte.
- `nginx` et `mariadb` tournent avec `cap_drop: ALL` et seulement les capacités strictement nécessaires à leur démarrage (`CHOWN`, `SETUID`, `SETGID`, et `DAC_OVERRIDE` pour MariaDB, `NET_BIND_SERVICE` pour nginx qui écoute sur un port privilégié dans le conteneur).
- `nginx` tourne en lecture seule (`read_only: true`) avec des `tmpfs` pour ses répertoires temporaires d'écriture.
- Aucun conteneur ne tourne en mode `privileged`.
- `wordpress` et `mariadb` ont des limites CPU/mémoire définies.

## Bonus

- **HTTPS** : certificat auto-signé généré par `scripts/gen-cert.sh`, nginx redirige automatiquement le port 8080 (HTTP) vers 8443 (HTTPS).
- **Profil admin** : phpMyAdmin n'est démarré qu'avec `docker compose --profile admin up -d`.
- **CI** : `.github/workflows/ci.yml` valide la syntaxe du `compose.yaml` et rejette tout conteneur privilégié ou mot de passe en clair.
