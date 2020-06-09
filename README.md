# JAVAX-EE - Enterprise alkalmazásfejlesztés Java EE környezetben

## Contexts and Dependency Injection

### Weld Java 12-vel

A Weld 3.0.5.Final verziója nem működik együtt 12-es Javaval. Ezért frissíteni kell, pl. a 3.1.1.Final
verzióra.

## Egyszerű Java EE alkalmazás

### Port

Amennyiben a port foglalt (ha pl. egy telepített alkalmazás használja), ahol a WildFly hallgatna, a következő kivételt dobja:

```
15:15:39,912 ERROR [org.jboss.msc.service.fail] (MSC service thread 1-7) MSC000001: Failed to start service org.wildfly.undertow.listener.default: org.jboss.msc.service.StartException in service org.wildfly.undertow.listener.default: Address already in use: bind /127.0.0.1:8080
        at org.wildfly.extension.undertow@15.0.1.Final//org.wildfly.extension.undertow.ListenerService.start(ListenerService.java:209)
```

A kivételből is látszik, hogy a `8080` port már foglalt, ezért nem tud elindulni sikeresen a WildFly.

Ekkor meg lehet adni, hogy tolja el a WildFly a portokat, más portokat használjon. Ekkor a következő parancssorral kell indítani:

```
standalone.bat -Djboss.socket.binding.port-offset=100
```

Ekkor a management port `9990` helyett `10090` lesz.

A wildfly-maven-plugin-t is konfigurálni kell, hogy az új portot használja:

```xml
<plugin>
    <groupId>org.wildfly.plugins</groupId>
    <artifactId>wildfly-maven-plugin</artifactId>
    <version>2.0.0.Final</version>
    <configuration>
        <port>10090</port>
    </configuration>
</plugin>
```

### JSP oldal

Az IDEA nem tudja feloldani a Standard JSP taglibeket. Ahhoz, hogy ne húzza alá pirossal, a következő függőséget kell
deklarálni a `pom.xml` fájlban:

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
    <scope>provided</scope>
</dependency>
```

## Űrlap

### Ékezetek kezelése

Webes alkalmazások esetén az űrlapon bevitt `ő` és `ű` karakterek utána nem jelennek meg helyesen.
Ekkor a megadott `EncodingFilter.java` osztályt kell alkalmazni.

```java
@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
```

## Java EE alkalmazás tesztelése

### Port az Arquillian pluginnél

Ha más portot adtunk meg a WildFly-nak, akkor az Arquilliant is konfigurálni kell, hogy ne
a default porton próbálkozzon, hanem az új porton. Ehhez el kell helyezni egy `arquillian.xml` állományt
az `src/test/resources` könyvtárban, a következő tartalommal:

```xml
<arquillian xmlns="http://jboss.org/schema/arquillian"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://jboss.org/schema/arquillian http://jboss.org/schema/arquillian/arquillian_1_0.xsd">

    <container qualifier="wildfly-remote" default="true">
        <configuration>
            <property name="managementPort">10090</property>
        </configuration>
    </container>

</arquillian>
```

## Adatbázis hozzáférés

### DataSource létrehozása

Ha el akarjuk érni az admin felületet, hozzá kell adni egy felhasználót a `bin/add-user.bat`
paranccsal.

Az adatbázis létrehozásánál figyelj, hogy az adatbázis kódolása `utf8_hungarian_ci` legyen.

MariaDB használata, és ennek drivere javasolt. Ennek az URL-jét használva nem kell külön
paramétereket megadni, elegendő a következő: `jdbc:mariadb://localhost/employees`

A Flyway `5.2.4` nem működik együtt a MariaDB driver `2.4.1` verziójával, ezért régebbit kell
használni. A [mariadb-java-client-2.3.0.jar](https://downloads.mariadb.com/Connectors/java/connector-java-2.3.0/mariadb-java-client-2.3.0.jar)
állományt kell letölteni!

Különben a következő hibát kapjuk:

```
org.flywaydb.core.api.FlywayException: Unsupported Database: MariaDB 10.1
```

A `bin/jboss-cli.bat` állományt elindítva a parancssorba beírandó:

```
connect
deploy "mariadb-java-client-2.3.0.jar"
/subsystem=datasources:installed-drivers-list
data-source add --name=EmployeeDS --jndi-name=java:/jdbc/EmployeeDS \
  --driver-name=mariadb-java-client-2.3.0.jar \
  --connection-url=jdbc:mariadb://localhost/employees \
  --user-name=employees \
  --password=employees
/subsystem=datasources:read-resource
/subsystem=datasources:read-resource(recursive=true)
```

PostgreSQL esetén:

```
connect
deploy "postgresql-42.2.6.jar"
/subsystem=datasources:installed-drivers-list
data-source add --name=EmployeeDS --jndi-name=java:/jdbc/EmployeeDS \
  --driver-name=postgresql-42.2.6.jar \
  --connection-url=jdbc:postgresql:employees \
  --user-name=employees \
  --password=employees
/subsystem=datasources:read-resource
/subsystem=datasources:read-resource(recursive=true)
```