# Architecture

## Réseaux

| Réseau | Membres | Rôle |
|---|---|---|
| edge-net | nginx | Seul réseau exposé à la machine hôte (ports 8080/8443) |
| frontend-net | nginx, wordpress | Trafic reverse proxy vers l'application |
| backend-net (internal) | wordpress, mariadb | Trafic applicatif vers la base, aucun accès possible depuis l'hôte |
| admin-net | phpmyadmin | Accès bonus à la base via le profil `admin` |

MariaDB n'est connecté qu'à `backend-net`, qui est déclaré `internal: true` : aucun port n'est publié sur l'hôte et aucune route ne sort de ce réseau. WordPress n'est connecté à aucun réseau exposé à l'hôte, il n'est donc joignable que via nginx.

## Vérification de l'isolation

```
docker compose port mariadb 3306
docker compose exec nginx wget -qO- http://wordpress:80 | head -1
docker compose exec wordpress getent hosts mariadb
```

La première commande ne retourne rien (pas de port publié). La seconde confirme que nginx atteint wordpress par son nom. La troisième confirme la résolution DNS interne de mariadb par wordpress.

## Healthchecks vs conteneur démarré

Un conteneur "démarré" (`Up`) signifie seulement que son process principal tourne. Un service "opérationnel" répond réellement aux requêtes attendues. `mariadb` est jugé opérationnel par `healthcheck.sh --connect`, `wordpress` par un test de connexion TCP sur le port 80. `nginx` et `wordpress` ne démarrent leur dépendance qu'une fois cette dernière `healthy` (`depends_on: condition: service_healthy`).

## Diagnostic d'une panne

1. `docker compose ps` — repérer le conteneur dont le statut n'est pas `healthy` / `running`.
2. `docker compose logs <service> --tail 50` — lire l'erreur précise.
3. `docker compose exec wordpress getent hosts mariadb` — écarter un problème DNS.
4. `docker compose exec mariadb healthcheck.sh --connect` — tester la base indépendamment de wordpress.
5. `docker stats` — écarter un dépassement des limites CPU/mémoire.
