#!/bin/sh
set -e
cd "$(dirname "$0")/.."

TIMESTAMP=$(date +%Y%m%d-%H%M%S)
DB_PASSWORD=$(cat secrets/db_password.txt)
DB_NAME=$(grep ^DB_NAME= .env | cut -d= -f2)
DB_USER=$(grep ^DB_USER= .env | cut -d= -f2)
PROJECT=$(grep ^COMPOSE_PROJECT_NAME= .env | cut -d= -f2)

docker compose exec -T mariadb sh -c "mariadb-dump -u$DB_USER -p$DB_PASSWORD $DB_NAME" > "backups/db-$TIMESTAMP.sql"

WP_VOLUME=$(docker volume ls -q --filter "label=com.docker.compose.project=$PROJECT" --filter "label=com.docker.compose.volume=wp-data")

docker run --rm \
  -v "$WP_VOLUME:/data:ro" \
  -v "$(pwd)/backups:/backup" \
  alpine tar czf "/backup/wp-data-$TIMESTAMP.tar.gz" -C /data .

echo "$TIMESTAMP"
