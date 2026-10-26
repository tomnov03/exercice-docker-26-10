#!/bin/sh
set -e
cd "$(dirname "$0")/.."

TIMESTAMP="$1"
DB_PASSWORD=$(cat secrets/db_password.txt)
DB_NAME=$(grep ^DB_NAME= .env | cut -d= -f2)
DB_USER=$(grep ^DB_USER= .env | cut -d= -f2)
PROJECT=$(grep ^COMPOSE_PROJECT_NAME= .env | cut -d= -f2)

cat "backups/db-$TIMESTAMP.sql" | docker compose exec -T mariadb sh -c "mariadb -u$DB_USER -p$DB_PASSWORD $DB_NAME"

WP_VOLUME=$(docker volume ls -q --filter "label=com.docker.compose.project=$PROJECT" --filter "label=com.docker.compose.volume=wp-data")

docker run --rm \
  -v "$WP_VOLUME:/data" \
  -v "$(pwd)/backups:/backup:ro" \
  alpine sh -c "rm -rf /data/* && tar xzf /backup/wp-data-$TIMESTAMP.tar.gz -C /data"

docker compose restart wordpress
