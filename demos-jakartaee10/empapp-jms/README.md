# Alkalmazás Jakarta 10-zel és WildFly 30-cal

Docker image előállítása:

```shell
docker build -t empapp --progress=plain  .
```

Lépései:

* MySQL JDBC driver letöltése az image-be
* Szkriptek másolása az image-be
* `execute.sh` script futtatása, `standalone-full.xml` alapján, hogy legyen JMS
  * WildFly indítása
  * `commands.cli` futtatása
    * JDBC driver hozzáadása
    * `java:/jdbc/EmployeeDS` DataSource létrehozása, környezeti változók alapján (trükk, hogy default értéket __kell__ megadni)
    * `java:/jms/queue/EmployeeQueue` JMS Queue létrehozása
  * WildFly leállítása
* WAR másolása a `deployments` könyvtárba

Indítás Docker Compose-zal:

```shell
cd employees
docker compose up -d
```