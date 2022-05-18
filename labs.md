# JAVAX-EE Enterprise alkalmazásfejlesztés Java EE környezetben gyakorlati feladatok

## A Java EE szabvány

Ellenőrizd, hogy a Java fel van-e telepítve a gépre (`C:\Program Files\Java`)!
Tedd be a path környezeti változók közé a `bin` könyvtárát, hogy parancssorból
futtatható legyen! Add ki a `java -version` parancsot!

Töltsd le a WildFly alkalmazásszerver 15.0.1.Final verzióját a
https://download.jboss.org/wildfly/15.0.1.Final/wildfly-15.0.1.Final.zip
címről! Csomagold ki, és indítsd el a `bin/standalone.bat` szkripttel!

## Contexts and Dependency Injection

Feladatként egy kedvenc helyeket nyilvántartó alkalmazást kell fejleszteni.
Egy kedvenc helyet a `Location` osztály reprezentál. Rendelkezik egy azonosítóval, névvel
és két koordinátával (rendre `Long id`, `String name`, `double lat`, `double lon`).

Hozz létre egy új projektet `locations` néven!

Létre kell hozni egy `LocationDao` és egy `LocationService` osztályt. Mindkettő CDI bean legyen!

A  `LocationDao` osztály a `Location` példányokat egy belső
listában tárolja. Ezt a `Collections.synchronizedList()` metódussal hozd létre, hogy szálbiztos legyen!
Az azonosító generálására vegyél fel egy `AtomicLong idGenerator` attribútumot, és
új kedvenc hely létrehozásakor hívd meg ennek az `incrementAndGet()` metódusát! (Ez így szintén szálbiztos.)

A `LocationDao` metódusai rendre:

* `List<Location> findAll()` - összes kedvenc hely listázása
* `Location save(String name, double lat, double lon)` - kedvenc hely létrehozása, visszaad egy objektumot, melynek az `id` attribútuma is ki van töltve
* `Location findById(long id)` - kedvenc hely keresése id alapján
* `Location update(long id, String name, double lat, double lon)` - kedvenc hely módosítása id alapján, visszaad egy módosított objektumot
* `void delete(long id)` - kedvenc hely törlése

A `LocationService` delegálja a kéréseket a `LocationDao` osztálynak. Legyen konstruktor injection.

A `LocationService` metódusai:

* `List<Location> listLocations()` - összes kedvenc hely listázása
* `Location createLocation(String name, double lat, double lon)` - kedvenc hely létrehozása
* `Location getLocationById(long id)` - kedvenc hely keresése id alapján
* `Location updateLocation(long id, String name, double lat, double lon)` - kedvenc hely módosítása id alapján
* `void deleteLocation(long id)` - kedvenc hely törlése

Írj egy `LocationMain` osztályt, mely elindítja a CDI konténert, lekéri a
`LocationService` beant, majd meghívja rajta a fenti metódusokat.

Írj egy unit tesztet a `LocationService` osztály metódusára, Mockito használatával (`@Mock` és
`@InjectMocks` annotációk használatával)!
Írj egy integrációs tesztet a `LocationService` osztály metódusaira! Pl. egy
`Location` példány létrehozása, elmentése, majd kilistázása.

A generált id a visszaadott példányból lekérhető.

## Egyszerű Java EE alkalmazás

Módosítsd az alkalmazást, hogy `war` típusú legyen! Módosítsd a `pom.xml`-ben
a függőségeket, vedd fel a megfelelő plugineket! Hozz létre egy servletet és egy
JSP-t, melynek segítségével listázni lehet a kedvenc helyeket! 
A videóban szereplő beanre itt nincs szükség, a servlet közvetlenül hívhatja
az előző feladatban létrehozott `LocationService` `listLocations()` metódusát. 
Ki kell írni a kedvenc helyek
összes tulajdonságát. A `LocationService` és a `LocationDao` osztályról
a `@Named` annotáció eltávolítható, és legyenek singleton session beanek!

## Űrlap

Egészítsd ki az alkalmazást egy űrlappal, amin új kedvenc helyet lehet felvenni!
Csak két beviteli mező legyen, az egyik első a név, a második a koordináták.
A koordinátákat egy szövegbeviteli mezőben lehessen megadni, a lebegőpontos számokat
vesszővel elválasztva. A servlet bontsa ezt szét latitude és longitude értékekre!

## Java EE alkalmazás tesztelése

Hozz létre Arquillian és Selenium integrációs teszteket!

## Adatbázis hozzáférés

Az adatbázisban hozz létre egy `locations` sémát, valamint egy `locations` felhasználót,
a megfelelő jogosultsággal!

```sql
use mysql;
create schema if not exists locations default character set utf8 collate utf8_hungarian_ci;

create user 'locations'@'localhost' identified by 'locations';
grant all on *.* to 'locations'@'localhost';

use locations;
```

Hozd létre a megfelelő táblát és szúrj be egy rekordot!

```sql
create table locations(id int auto_increment primary key, name varchar(255), lat double, lon double);

insert into locations(name, lat, lon) values ('Budapest', 47.4979, 19.0402);
```

Hozz létre egy `LocationDao` osztályt, és abba implementáld a metódusokat service-ben
szereplő metódusokat JDBC használatával! A service delegálja a kéréseket a dao beannek!

## Séma inicializálás Flyway eszközzel

Indítsd el a `Flyway` migrációt, mely inicializálja az adatbázist! Hozd létre az SQL állományt,
mely létrehozza a táblát.

## JPA

Írd át a `LocationDao` osztályt, hogy JPA műveletekkel működjön!

## Tranzakciókezelés

Implementálj adatbázisba audit naplózást!

Írj egy `AuditLog` osztályt, melynek van egy `Long id` és egy `String message`
attribútuma! Írj egy `AuditLoggerDao` stateless session beant!
Írj hozzá egy `AuditLoggerService` stateless session beant! A `LocationService` írási műveletek
esetén hívja meg az `AuditLoggerService` `saveAuditLog()` metódusát! A `saveAuditLog`
metódus indítson saját tranzakciót! Legyen
lekérdezési lehetőség is a `listAuditLogs()` metódussal! (Ne felejtsd el létrehozni a
táblát is Flyway migrációs szkripttel!)

## Enterprise JavaBeans

Hozz létre egy application client alkalmazást, mely távolról tudja hívni a
`LocationService` metódusait!

## EJB Stateful session bean

Hozz létre egy `LocationStepsService` stateful session beant, melynek van egy
`void addLocation(Location location)` metódusa, mely távolról hívható! Ezzel
egy listához lehet hozzáadni a `Location` példányokat. Írj egy `saveAll()`
metódust, mely a végén meghívja a `LocationService` `void save(String name, double lat, double lon)` metódusát
mindegyik listában szereplő elemre! Írj egy `cancel()` metódust, mely
úgy fejezi be a működést, hogy nem menti el a felvett `Location` példányokat!

## EJB Singleton Session bean

Implementálj egy `LocationCache` singleton session beant, mely a memóriában egy
listában tárolja a `Location` példányokat. Abban az esetben, ha meghívásra kerül
a `LocationService` `List<Location> findAll()` metódusa, vizsgálja meg, hogy
a `LocationCache` listája `null`-e. Ha igen, akkor kérdezze le az adatbázisból
az értékeket, és töltse fel a `LocationCache` listáját! Ha nem `null`, akkor
adja vissza a `LocationCache` listájában lévő értékeket!

## EJB Callback metódusok

Amikor a `LocationCache` elindul (és induljon el az alkalmazás elindításakor),
akkor a `LocationDao` felhasználásával kérje le a `Location` példányokat, és
töltse fel a saját listáját. Írja is ki a konzolra, hogy ez megtörtént.

## EJB Timer Service

Írj a `LocationService`-be egy metódust, mely öt percenként fut, és azt vizsgálja,
hogy került-e be az adatbázisba olyan hely, amelynek a koordinátája `0.0, 0.0`.
Amennyiben igen, írjon ki egy üzenetet a konzolra!

## JSF

Cseréld le a Java servlet és JSP megvalósítást JSF-re! Ebben a feladatban csak
a listázó képernyőt implementáld! Az űrlapot még ne helyezd fel!

## JSF űrlap

Implementáld az űrlapot JSF-fel!

## Bean Validation

Csak a Bean Validationre hozz létre egy külön projektet, melybe másold át
a `Location` osztályt! A név nem lehet üres, a latitude -90 és +90
között kell lennie, a longitude -180 és +180 között kell lennie!

Írj teszt esetet, mely ellenőrzi, hogy működik-e a validáció!

## Bean Validation saját implementáció

Hozz létre egy saját validációs annotációt `@Coordinate` néven!
Ennek legyen egy `Type` nevű enum paramétere, melynek értékei
`Type.LAT`, `Type.LON`. Írj hozzá validációt, mely leellenőrzi, hogy a
koordináták megfelelőek-e. (Csak `double` típusra rakható.) Írj rá tesztesetet!

## Bean Validation többnyelvűség

Implementáld, hogy a `Location` osztályra tett validációk a hibaüzeneteket
különböző nyelveken adják vissza! Írj rá tesztesetet!

## Bean Validation Validation Group

Implementáld, hogy a `CreateLocation` validation group esetén az `id`
mezőnek üresnek kell lennie, `UpdateLocation` validation group esetén
meg egy pozitív egész számot tartalmazhat csak (nem lehet nulla)!

## Bean Validation Java EE környezetben

Ellenőrizd, hogy a név nem-e üres, valamint azt, hogy a koordináták a megfelelő
formátumban vannak-e beírva! A JSF kontrollerben lévő attribútumra helyezd el
a Bean Validation annotációkat!

## Naplózás

A `LocationService`-ben minden módosító műveletnél legyen SLF4J naplózás `debug` szinten, és ez jelenjen is meg a
konzolon.

## SOAP webszolgáltatások

Írj egy SOAP webszolgáltatást, mely visszaadja a kedvenc helyeket! Teszteld SoapUI-jal!

## RESTful webszolgáltatások

Implementáld a CRUD műveleteket a `Location` entitásra! Legyen RESTful művelet
kedvenc helyek listázására, egyedi azonosító alapján lekérésre, létrehozásra,
módosításra és törlésre! Teszteld Postmannel!

## Aszinkron üzenetkezelés JMS-sel

Írd meg úgy a naplózást, hogy amikor egy `Location` létrehozásra kerül
(`LocationService`-ben), dobjon egy üzenetet egy sorba, melyet az
`AuditLoggerService` kap el, és ennek alapján írja meg a naplót.
Azaz _ne_ metódushívással hívja a `LocationService` az `AuditLoggerService`-t!
