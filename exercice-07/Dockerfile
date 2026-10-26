FROM mysql:8.4

ENV MYSQL_ROOT_PASSWORD=rootpass
ENV MYSQL_DATABASE=kennelDB

COPY init.sql /docker-entrypoint-initdb.d/init.sql
