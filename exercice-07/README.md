# Exercice 07 — kennelDB

```
docker build -t kennel-mysql .
docker run -d --name kennel-mysql -p 3306:3306 kennel-mysql
```

Vérification :
```
docker exec -it kennel-mysql mysql -uroot -prootpass -e "SHOW TABLES;" kennelDB
```
