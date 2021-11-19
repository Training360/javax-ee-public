class: inverse, center, middle

# Enterprise alkalmazásfejlesztés Java EE környezetben

---
class: inverse, center, middle


## Tematika

---

## Tematika 1.

---

## Források


---
class: inverse, center, middle



## A Java EE szabvány

---

## Java EE

* Specifikációgyűjtemény nagyvállalati alkalmazásfejlesztésre
  * Un. esernyő szabvány
  * Java Community Process
* Nagyvállalati alkalmazás: Java SE által nem támogatott tulajdonságok
* Implementációi: különböző alkalmazásszerverek

---

class: split-50

## Nagyvállalati alkalmazás

<div>
.column[
* Perzisztencia
* Többszálúság
* Tranzakció-kezelés
* Távoli elérés
* Névszolgáltatás
]
.column[
* Skálázhatóság
* Magas rendelkezésre állás
* Aszinkron üzenetkezelés
* Biztonság
* Monitorozás és beavatkozás
]
</div>

---

## Java EE története

* Sun azonosította, hogy újra és újra felmerülő követelményekre szabványos válasz legyen
  * Java 2 Platform, Enterprise Edition (J2EE) 1.2 (1999-ben)
* Oracle
* 2017\. szeptemberében átkerült az Eclipse alapítványhoz
* 2018-ban átnevezve Jakarta EE-re

---

## Java EE tulajdonságai

* POJO-kra épülő komponens modell, melyeket konténerként tartalmaz
  * Konténer vezérli az életciklusát
* Többrétegű alkalmazás architektúra
* Dependency Injection, Inversion of Control
* Aspektusorientált programozás támogatása
* Fejlesztők az üzleti problémák megoldására <br /> koncentráljanak
* Hordozhatóság az alkalmazásszerverek között

---

## Háromrétegű alkalmazás

![Java EE rétegek](images/javaee-tiers.png)

---

## Alkalmazás felépítése

* Csomag (EAR, WAR)
  * Komponensek, pl. class fájlok
  * Telepítésleírók, deployment descriptorok
  * Erőforrás állományok, pl. képek, statikus tartalmak
* Modulokba szervezhető (belső JAR, WAR, stb. állományok)
* Alkalmazásszerverre telepítés: deploy folyamat

---

## EJB bean

```java
@Stateless
public class HelloBean {

    public String sayHello(String name) {
        return String.format("Hello %s!", name);
    }
}
```

---

## Servlet

```java
@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Inject
    private HelloBean helloBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try (PrintWriter w = resp.getWriter()) {
            String name = req.getParameter("name");
            String message = helloBean.sayHello(name);
            w.println(message);
        }
    }
}
```

---

## Alkalmazásszerver

* Specifikációkat implementálja
* Tartalmazza az EJB konténert
* Alkalmazásszerverek
  * GlassFish - nyílt forráskódú
  * WildFly - nyílt forráskódú
  * Red Hat JBoss Enterprise Application Platform
  * Open Liberty - nyílt forráskódú
  * IBM WebSphere Application Server Liberty

---

## Specifikációk - Közös és üzleti komponensek

* Contexts and Dependency Injection for the Java EE Platform (CDI) - DI
* Bean Validation - validáció, ellenőrzés a beaneknél
* Enterprise JavaBeans (EJB) és Interceptors - üzleti komponensek és folyamatok implementálására
* Java Transaction API (JTA) - tranzakciókezelés

---

## Specifikációk - Perzisztencia

* Java Persistence API (JPA) - Java objektumok perzisztenciájára, ORM

---

## Specifikációk - Web réteg

* Servlet - HTTP protokoll kezelésére, alapkomponense a Servlet
* JavaServer Pages (JSP) - dinamikus (főleg) HTML generálására, template engine, alapkomponense a JSP oldal
* JavaServer Pages Standard Tag Library (JSTL) - JSP-n belüli vezérlésekre, pl. feltételek, ciklusok, alapkomponense a tag
* Java API for WebSocket - WebSocket (full-duplex kommunikáció) kezelésére Javaból

---

## Specifikációk - Web réteg

* JavaServer Faces (JSF) - komponens alapú webes UI előállítására
* Unified Expression Language (EL) - apró nyelv, JSP és JSF oldalakban kifejezések megadására

---

## Specifikációk - Integráció és webszolgáltatások

* Java API for XML Binding (JAXB) - Java objektumok és XML dokumentumok közötti megfeleltetés
* Java API for XML Web Services (JAX-WS) - SOAP webszolgáltatások
* Java API for JSON Processing (JSON-P) - JSON alacsony szintű kezelésére
* Java API for JSON Binding (JSON-B) - Java objektumok és JSON dokumentumok közötti megfeleltetés
* Java Message Service (JMS) - aszinkron üzenetküldés <br /> üzenetsorokon keresztül
* JavaMail - email küldés

---

class: inverse, center, middle



## Contexts and Dependency Injection for the Java EE Platform

---

## Inversion of Control

* A program részei egy újrafelhasználható keretrendszertől kapják meg a vezérlést
* Komponensek és konténer
* Növeli a modularitást és a kiterjeszthetőséget
* Robert C. Martin és Martin Fowler terjesztette el
* "Don't call us we will call you"

---

## Dependency Injection

* Tervezési minta (nem GoF)
* Inversion of Control egy implementációja a függőségekkel kapcsolatban
* Az objektumok nem maguk példányosítják vagy kérik le a függőségeiket
* Csak leírják, mire van szükségük
* A komponenseket a konténer példányosítja és köti össze
* Egy központosított vezérlés a komponensek <br /> életciklusa és kapcsolatai fölött

---

## Dependency Injection előnyei

* Low coupling (laza kapcsolat)
* Implementációk cserélhetősége
* Tesztelés segítése (test double objektumok használata: pl. dummy, stub, mock)
* Újrafelhasználhatóság növelése, boilerplate kód csökkentése

---

## CDI

* CDI konténer
* Jól definiált bean életciklus
* Dependency injection
* Scope: request, session, application, conversation
* AOP támogatás, interceptor
* Eseménykezelés
* Beépül más szabványokba
* Java SE-n belül is használható
* Referenciaimplementáció: Weld

---

## CDI bean

```java
@Named
public class EmployeeDao {

    private List<String> employees = Collections.synchronizedList(new ArrayList<>());

    public void saveEmployee(String name) {
        employees.add(name);
    }

    public List<String> listEmployees() {
        return new ArrayList<>(employees);
    }
}
```

---

## Klasszikus függőség

```java
public class EmployeeService {

    private EmployeeDao employeeDao;

    public EmployeeService() {
        this.employeeDao = new EmployeeDao();
    }

    public void saveEmployee(String name) {
        String trimmedName = name.trim();
        employeeDao.saveEmployee(trimmedName);
    }

    public List<String> listEmployees() {
        return employeeDao.listEmployees();
    }
}
```

---

## Dependency injection

```java
@Named
public class EmployeeService {

    private EmployeeDao employeeDao;

    @Inject
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void saveEmployee(String name) {
        String trimmedName = name.trim();
        employeeDao.saveEmployee(trimmedName);
    }

    public List<String> listEmployees() {
        return employeeDao.listEmployees();
    }
}
```

---

## Függőségek

```xml
<dependency>
    <groupId>javax.enterprise</groupId>
    <artifactId>cdi-api</artifactId>
    <version>2.0.SP1</version>
</dependency>
<dependency>
    <groupId>org.jboss.weld.se</groupId>
    <artifactId>weld-se-shaded</artifactId>
    <version>3.0.5.Final</version>
</dependency>
```

---

## beans.xml állomány

* Classpath-on a `META-INF/beans.xml` helyen
* Maven esetén `src/main/resources/META-INF/beans.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://xmlns.jcp.org/xml/ns/javaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
            http://xmlns.jcp.org/xml/ns/javaee/beans_2_0.xsd"
       bean-discovery-mode="all" version="2.0">
</beans>
```

---

## Konténer indítás

```java
try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
    EmployeeService employeeService = container.select(EmployeeService.class).get();
    employeeService.saveEmployee("  John Doe  ");

    List<String> employees = employeeService.listEmployees();
    System.out.println(employees);
}
```

---

## Injektálás fajtái

* Konstruktor injection
  * Kötelező függőségek
  * Unit tesztelésnél, példányosításkor kötelező megadni
* Setter injection
  * Opcionális függőségeknél
* Attribute injection
* Keverhetők

---

class: inverse, center, middle



## CDI tesztelése

---

## Függőségek

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>2.23.4</version>
    <scope>test</scope>
</dependency>
```

---

## Unit teszt

```java
public class EmployeeServiceTest {

    private EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);

    private EmployeeService employeeService = new EmployeeService(employeeDao);

    @Test
    public void testSaveEmployee() {
        employeeService.saveEmployee("  John Doe  ");
        verify(employeeDao).saveEmployee("John Doe");
    }
}
```

---

## Unit teszt DI-nel

```java
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceInjectTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testSaveEmployee() {
        employeeService.saveEmployee("  John Doe  ");
        verify(employeeDao).saveEmployee("John Doe");
    }
}
```

---

## Integrációs teszt konténer indítással

```java
public class EmployeeServiceIntegrationTest {

    @Test
    public void testSaveAndList() {
        try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            EmployeeService employeeService = container.select(EmployeeService.class).get();
            employeeService.saveEmployee("  John Doe  ");
            assertEquals(List.of("John Doe"), employeeService.listEmployees());
        }
    }
}
```

---

class: inverse, center, middle



## Egyszerű Java EE alkalmazás

---

## Egyszerű alkalmazás felépítése

* Model 2

![Egyszerű alkalmazás](images/simpleapp-arch.png)

---

## Projekt

![Projekt struktúra](images/simpleapp-project.png)

---

## Függőségek

```xml
<dependencies>
   <dependency>
       <groupId>javax</groupId>
       <artifactId>javaee-api</artifactId>
       <version>8.0</version>
       <scope>provided</scope>
   </dependency>
</dependencies>
```

---

## Maven pluginek

```xml
<build>
    <finalName>simpleapp</finalName>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-war-plugin</artifactId>
            <version>3.2.2</version>
            <!-- <configuration>
                <failOnMissingWebXml>false</failOnMissingWebXml>
            </configuration> -->
        </plugin>
        <plugin>
            <groupId>org.wildfly.plugins</groupId>
            <artifactId>wildfly-maven-plugin</artifactId>
            <version>2.0.0.Final</version>
        </plugin>
    </plugins>
</build>
```

---

## EJB

```java
@Singleton
public class EmployeesBean {

    private List<Employee> employees =
            Collections.synchronizedList(
                    new ArrayList<>(
                            List.of(new Employee("John Doe"),
                                    new Employee("Jane Doe"))));

    public List<Employee> listEmployees() {
        return new ArrayList<>(employees);
    }
}
```

---

## Servlet

```java
@WebServlet(urlPatterns = "/employees")
public class EmployeesServlet extends HttpServlet {

    @Inject
    private EmployeesBean employeesBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Employee> employees = employeesBean.listEmployees();
        req.setAttribute("employees", employees);
        req.getRequestDispatcher("/WEB-INF/jsp/employees.jsp").forward(req, resp);
    }
}
```

---

## JSP oldal

```xml
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employees</title>
</head>
<body>

    <table>
        <tr><th>Name</th></tr>

        <c:forEach items="${employees}" var="employee">
            <tr>
                <td>${employee.name}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
```

---

## Alkalmazás

* `mvn clean package` hatására létrejön a `target/simpleapp.war` állomány

![WAR](images/simpleapp-war.png)

---

## WildFly alkalmazásszerver

* Red Hat/JBoss
* Nyílt forráskódú
* Letölthető a [http://www.wildfly.org/](http://www.wildfly.org/) címről
* 2019\. januárjában jelent meg a 15.0.1.Final verzió, Java EE 8
* Kicsomagolható, és a `bin\standalone.bat` állománnyal indítható

---

## Deploy

* `mvn wildfly:deploy` paranccsal telepíthető a lokálisan indított alkalmazásszerverre
* Elérhető böngészőből a `http://localhost:8080/employees` címen
* Admin konzol elérhető a `http://localhost:9990/` címen
* Felhasználó hozzáadása a `bin\add-user.bat` paranccsal

---

class: inverse, center, middle



## Űrlap kezelése

---

## HTML űrlap

```xml
<form method="post">
    <input type="text" name="name" />
    <input type="submit" value="Create employee" />
</form>
```

---

## Servlet

```java
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    employeesBean.createEmployee(new Employee(req.getParameter("name")));
    resp.sendRedirect("employees");
}
```

---

## Bean

```java
public void createEmployee(Employee employee) {
    employees.add(employee);
}
```

---
class: inverse, center, middle



## Java EE alkalmazás tesztelése

---

## Tesztelési módszerek

* Unit tesztelés - osztályok önmagukban, alkalmazásszerver nélkül
* Integrációs tesztelés, pl. Arquillian
* E2E tesztelés, pl. Selenium
* Arquillian és Selenium integrálható

---

## Unit tesztelés

* EJB beanek, servletek alkalmazásszerver szolgáltatásokat használnak
* Ezen szolgáltatások mockolása bonyolult
* Válasszuk le a logikát külön osztályba, melyek nem függenek a Java EE szabványoktól

---

## Külön POJO

```java
@Named
public class EmployeeNameTrimmer {

    public Employee trimName(Employee employee) {
        employee.setName(employee.getName().trim());
        return employee;
    }
}
```

---

## Használata EJB-ből

```java
@Singleton
public class EmployeesBean {

  @Inject
  private EmployeeNameTrimmer employeeNameTrimmer;

  public void createEmployee(Employee employee) {
        employee = employeeNameTrimmer.trimName(employee);
        employees.add(employee);
  }
}
```

---

## Teszteset a POJO-ra

```java
public class EmployeeNameTrimmerTest {

    @Test
    public void testTrimName() {
        assertEquals("John Doe",
                new EmployeeNameTrimmer()
                        .trimName(new Employee("  John Doe  "))
                        .getName());
    }
}
```

---

## Integrációs tesztelés Arquilliannal

* JBoss projekt
* Nyílt forráskódú
* Integrációs teszt keretrendszer
* Képes kiválasztani egyes komponenseket, és azokat deployolni beágyazott vagy
  távoli alkalmazásszerverre, majd komponenseket közvetlenül meghívni
* Best practice: távoli alkalmazásszerver

---

## Arquillian függőségek

```xml
<dependency>
    <groupId>org.jboss.arquillian.core</groupId>
    <artifactId>arquillian-core-api</artifactId>
    <version>1.4.1.Final</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.jboss.arquillian.junit</groupId>
    <artifactId>arquillian-junit-container</artifactId>
    <version>1.4.1.Final</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.wildfly.arquillian</groupId>
    <artifactId>wildfly-arquillian-container-remote</artifactId>
    <version>2.1.1.Final</version>
    <scope>test</scope>
</dependency>
```

---

## Arquillian integrációs teszteset

```java
@RunWith(Arquillian.class)
public class EmployeesBeanTest {

    @Inject
    private EmployeesBean employeesBean;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(Employee.class, EmployeesBean.class);
    }

    @Test
    public void testSaveThenList() {
        employeesBean.createEmployee(new Employee("John Doe"));
        List<Employee> employees = employeesBean.listEmployees();
        employees.stream()
            .filter(e -> e.getName().equals("John Doe"))
            .findFirst().get();
    }
}
```

---

## Selenium

* Böngésző automatizálás, tipikusan webes alkalmazások tesztelésére
* Egyéb automatizálási feladatokra
* Különböző programozási nyelvekhez illesztés: C#, Groovy, Java, Perl, PHP, Python, Ruby and Scala
* Képes meghajtani a különböző böngészőket is
* Platformfüggetlen
* Nyílt forráskódú, ingyenes

---

## Architektúra

* Teszteset
* Selenium Client API
* Selenum WebDriver, böngészőnként
  * Firefoxhoz geckodriver, https://github.com/mozilla/geckodriver
* Böngésző

---

## Függőség

```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>3.141.59</version>
    <scope>test</scope>
</dependency>
```

---

## Selenium teszteset

```java
public class EmployeesSeleniumTest {
    @Test
    public void testSaveThenList() {
        System.setProperty("webdriver.gecko.driver",
          "C:\\Java\\geckodriver-v0.23.0-win64\\geckodriver.exe");
        FirefoxDriver driver = new FirefoxDriver();

        driver.get("http://localhost:8080/simpletest/employees");

        driver.findElementById("name-input").sendKeys("John Doe");
        driver.findElementById("employee-form").submit();

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(presenceOfElementLocated(
            By.xpath("//td[text() = 'John Doe']")));

        List<WebElement> tds = driver
            .findElements(By.xpath("//td"));
        assertEquals("John Doe", tds.get(tds.size() - 1).getText());

        driver.close();
    }
}
```

---

## Selenium továbbfejlesztési lehetőségek

* Közös Driver inicializálás
* Page object model
* Integráció Arquillianhoz

---

class: inverse, center, middle



## Adatbázis hozzáférés

---

## Részei

* Adatbázis séma
* JDBC driver
* DataSource
* DAO

---

## Adatbázis séma

```sql
use mysql;
create schema if not exists employees default character set utf8 collate utf8_hungarian_ci;

create user 'employees'@'%' identified by 'employees';
grant all on *.* to 'employees'@'%';

create table employees (id bigint auto_increment, emp_name varchar(255),
      constraint pk_employee primary key (id));
```

---

## Relációs adatbázishoz való hozzáférés

* JDBC Driver implementálja a JDBC API-t
* JDBC Driver az alkalmazásszerver része legyen, ne az alkalmazásé
* MariaDB-hez letölthető: https://mariadb.com/downloads/#connectors

---

## JDBC url

* Adatbázis szerver elérésének megadására, Java specifikus
* `jdbc:[gyártó]:[adatbázis specifikus elérési út]`  
* `jdbc:mysql://localhost:3306/employees?useUnicode=true`
* `jdbc:oracle:thin:@localhost:1521:employees`
* `jdbc:sqlserver://localhost\employees`
* `jdbc:h2:mem:db;DB_CLOSE_DELAY=-1`

---

## DataSource

* Connection factory
* Connection pool
* Alkalmazásszerveren belül konfigurálható

---

## DataSource beállítása WildFly-on

A `jboss-cli.bat` állományt elindítva a parancssorba beírandó:

```
connect
deploy "mariadb-java-client-2.3.0.jar"
/subsystem=datasources:installed-drivers-list
data-source add --name=EmployeeDS --jndi-name=java:/jdbc/EmployeeDS \
  --driver-name=mariadb-java-client-2.3.0.jar \
  --connection-url=jdbc:mysql://localhost/employees \
  --user-name=employees \
  --password=employees
/subsystem=datasources:read-resource
/subsystem=datasources:read-resource(recursive=true)
```

Ékezetekhez:

```
jdbc:mysql://localhost/employees?useUnicode=true
  &useJDBCCompliantTimezoneShift=true
  &useLegacyDatetimeCode=false
  &serverTimezone=UTC
```

---

## DAO

* Műveletek DAO-ban (Data Access Object - Java EE tervezési minta)
* DataSource hozzáférés DI-vel

---

## DAO példa

```java
@Stateless
public class EmployeesDao {

    @Resource(mappedName = "java:/jdbc/EmployeeDS")
    private DataSource dataSource;

    public List<Employee> listEmployees() {
        // ...
    }

    public void createEmployee(Employee employee) {
        // ...
    }

}
```

---

## JDBC lekérdezés

```java
public List<Employee> listEmployees() {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement("select id, emp_name from employees");
         ResultSet rs = ps.executeQuery()
    ) {
        List<Employee> employees = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String name = rs.getString("emp_name");
            employees.add(new Employee(id, name));
        }
        return employees;
    }
    catch (SQLException ioe) {
        throw new IllegalStateException("Can not query employees"
            , ioe);
    }
}
```

---

## JDBC beszúrás

```java
public void createEmployee(Employee employee) {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement("insert into employees(emp_name) values (?)");
    ) {
        ps.setString(1, employee.getName());
        ps.executeUpdate();
    }
    catch (SQLException ioe) {
        throw new IllegalStateException("Cannot insert employee", ioe);
    }
}
```

---

class: inverse, center, middle



## Séma inicializálás Flyway eszközzel

---

## Séma inicializálás

* Adatbázis séma létrehozása (táblák, stb.)
* Változások megadása
* Metadata table alapján  

---

## Elvárások

* SQL/XML leírás
* Platform függetlenség
* Lightweight
* Visszaállás korábbi verzióra
* Indítás paranccssorból, alkalmazásból
* Cluster támogatás
* Placeholder támogatás
* Modularizáció
* Több séma támogatása

---

## Flyway függőség

```xml
<dependency>
  <groupId>org.flywaydb</groupId>
  <artifactId>flyway-core</artifactId>
  <version>5.2.4</version>
</dependency>
```

---

## Séma inicializálás Flyway eszközökkel

```java
@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class DatabaseMigration {

    @Resource(mappedName = "java:/jdbc/EmployeeDS")
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource).load();
        flyway.migrate();
    }
}
```

---

## Migrációs szkriptek

`src/main/resources/db/migration/V1__employees.sql` állomány

```sql
create table employees (id bigint auto_increment,
  emp_name varchar(255),
    constraint pk_employee primary key (id));
```

`flyway_schema_history` tábla

---

class: inverse, center, middle



## JPA

---

## JPA

* JDBC bonyolultsága: leképzés a relációs adatbázis és oo világ között
* Megoldás: keretrendszer biztosítsa konfiguráció alapján
* ORM: Object-Relational Mapping
* Szabvány: JPA
* Implementációi: Hibernate, EclipseLink, OpenJPA
* JDBC-re épül

---

## Entitások

```java
@Entity
public class Employee {

    @Id
    private Long id;

    private String name;

    public Employee() {
    }

    // Getter és setter metódusok
}
```

```sql
create table employee (id bigint,
  name varchar(255),
    constraint pk_employees primary key (id));
```

---

## Személyre szabás

```java
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private Long id;

    @Column(name = "emp_name")
    private String name;

    // Getter és setter metódusok
}
```

```sql
create table employees (id bigint,
  emp_name varchar(255),
    constraint pk_employees primary key (id));
```

---

## Azonosítógenerálás

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

```sql
create table employees (id bigint auto_increment,
  emp_name varchar(255),
    constraint pk_employees primary key (id));
```


---

## EntityManager

* CRUD műveletek

```java
Employee employee = new Employee();
employee.setName("John Doe");
entityManager.persist(employee);
```

```java
Employee employee = entityManager.find(Employee.class, 1);
employee.setName('Jack Doe');
```

```java
Employee employee = entityManager.find(Employee.class, 1);
employee.remove(employee);
```

```java
entityManager
  .createQuery("select e from Employee e order by e.name",
      Employee.class)
    .getResultList();
```

---

## EntityManager hozzáférés

* `EntityManagerFactory` (persistence context) előre konfigurálása `persistence.xml` alapján
* `EntityManager` injektálás `@PersistenceContext` annotációval

---

## Persistence unit

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
    http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
  <persistence-unit name="pu" transaction-type="JTA">
      <jta-data-source>jdbc/EmployeeDS</jta-data-source>
    <exclude-unlisted-classes>false</exclude-unlisted-classes>
    <properties>
            <property name="javax.persistence.schema-generation.database.action"
                value="drop-and-create"/>
            <property name="hibernate.show_sql" value="true"/>
    </properties>
  </persistence-unit>
</persistence>
```

---

## DAO

```java
@Stateless
public class EmployeesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    public List<Employee> listEmployees() {
        return entityManager
            .createQuery("select e from Employee e", Employee.class)
                 .getResultList();
    }
}
```

---

class: inverse, center, middle



# Tranzakciókezelés

---

## Tranzakciókezelés

* ACID tulajdonságok
  * Atomicity
  * Consistency
  * Isolation
  * Durability

---

## Két fajta megközelítés

* Programozott tranzakciókezelés
* Deklaratív tranzakciókezelés

---

## Programozott tranzakciókezelés

```java
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class EmployeesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private UserTransaction ut;

    public void createEmployee(Employee employee) {
      try {
        ut.begin();
        entityManager.persist(employee);
        ut.commit();
      }
      catch (Exception e) {
        ut.rollback();
      }
    }

}
```

---

## Deklaratív tranzakciókezelés

```java
@Stateless
public class EmployeesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createEmployee(Employee employee) {
        entityManager.persist(employee);
    }

}
```


---

## Propagáció

* `@TransactionAttribute` annotációval
* `TransactionAttributeType` enum

![Propagáció](images/propagacio.png)

---

## Propagációs tulajdonságok

* `REQUIRED` (default): ha nincs tranzakció, indít egyet, ha van csatlakozik hozzá
* `REQUIRES_NEW`: mindenképp új tranzakciót indít
* `SUPPORTS`: ha van tranzakció, abban fut, ha nincs, nem indít újat
* `MANDATORY`: ha van tranzakció, abban fut, ha nincs, kivételt dob
* `NOT_SUPPORTED`: ha van tranzakció, a tranzakciót felfüggeszti, ha nincs, nem indít újat
* `NEVER`: ha van tranzakció, kivételt dob, ha nincs, <br /> nem indít újat

---

## Izoláció

* Java EE nem támogatja a tranzakciónkénti izolácós szint állítását
* Izolációs problémák:
    * dirty read
    * non-repetable read
    * phantom read
* Izolációs szintek:
    * read uncommitted
    * read commited
    * repeatable read
    * serializable

---

## Visszagörgetési szabályok

* Konténer dönt a commitról vagy rollbackről
* Kivételek esetén:
  * Checked kivétel: commit  
  * Unchecked kivétel: rollback
  * Felülbírálható: `@ApplicationException(rollback = true)`
  * Felülbírálható:

```java
@Transactional(rollbackOn = SQLException.class,
    dontRollbackOn = {ArrayIndexOutOfBoundsException.class,
        IllegalArgumentException.class})
```

---

## Rollbackre explicit módon megjelölni

* `SessionContext` injektálás
* `setRollbackOnly()` és `getRollbackOnly()` metódusok

---

## Visszagörgetésre megjelölés

```java
@Stateless
public class EmployeesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private SessionContext ctx;

    @Transactional
    public void createEmployee(Employee employee) {
        entityManager.persist(employee);

        if (employee.getName().equals("")) {
            ctx.setRollbackOnly();
        }
    }

}
```

---

class: inverse, center, middle


## Enterprise JavaBeans

---

## EJB

* Szerveroldali Java komponens, melyet a konténer tartalmaz
* Üzleti logika (best practice: üzleti logika kiszervezése POJO-kba)
* POJO, annotációkkal
* Életciklusát (példányosítás) a konténer vezérli
* Hozzáfér konténer szolgáltatásokhoz - ennyivel több a CDI Beaneknél

---

## EJB típusok

* Stateless session bean
* Stateful session bean
* Singleton session bean
* Message driven bean

---
class: split-50

## Konténer szolgáltatások

<div>
.column[
* Távoli elérés
* Dependency injection: más beanek és külső erőforrások
  * Adatbázis, JMS sor, mail server
* Pool (stateless session bean, MDB)
* Életciklus kezelés, példányosítás
* Tranzakciókezelés
* Biztonság
* AOP
]
.column[
* Párhuzamosság
* Aszinkron hívás
* Ütemezés
* Magas rendelkezésreállás és skálázhatóság
]
</div>

---

## Távoli elérés

* Implementálhatnak üzleti interfészeket
* Interfészek lehetnek helyi (local) vagy távoli (remote) interfészek
* Lokális interfészben definiált metódusok elérhetőek az adott JVM-ből
* Távoli interfészben definiált metódusok elérhetőek más JVM-ből
* `@Local` és `@Remote` annotációk

---

## JNDI

* Java Naming and Directory Interface
* Névszolgáltatás
* Egységes JNDI nevekkel kerülnek az EJB-k bejegyzésre

```
java:<scope>[/<app-name>]/<module-name>/<bean-name>[!<fully-qualified-interface-name>]
```

---

## JNDI nevek

```
14:02:41,176 INFO  [org.jboss.as.ejb3.deployment] (MSC service thread 1-1) WFLYEJB0473:
JNDI bindings for session bean named 'EmployeesDao'
in deployment unit 'deployment "empapp.war"' are as follows:

java:global/empapp/EmployeesDao!employees.EmployeesDao
java:app/empapp/EmployeesDao!employees.EmployeesDao
java:module/EmployeesDao!employees.EmployeesDao
ejb:empapp/EmployeesDao!employees.EmployeesDao
java:global/empapp/EmployeesDao
java:app/empapp/EmployeesDao
java:module/EmployeesDao
```

---

## Stateless session bean

* Állapotmentes
* "Rövid élettartamú, egy metódus hívásig él" - lsd. pooling
* Egy kérés-válasz erejéig
* `@Stateless` annotáció

---

class: inverse, center, middle


## Stateful session bean

---

## Stateful session bean

* Állapottal rendelkező
* Kliensenként egy példány
* Web sessionönként egy példány
* Hívások között megőrzi az állapotot, un. _conversional state_
    * `@Remove` annotációval jelzett metódus hívása jelzi a végét
* `@Stateful` annotáció

---

## Passziválás

* Ha a memóriában tartható beanek száma elér egy maximumot
    * Passziválás (last recently used), perzisztens állapot
* Timeout: `@StatefulTimeout(value = 20, unit = TimeUnit.SECONDS)`

---

## Alternatívák

* Állapot tárolása kliens oldalon, utaztatása metódus paraméterként
* Web session
* Azonosító tárolása kliens oldalon, tárolása adatbázisban

---
class: inverse, center, middle


## Singleton session bean

---

## Singleton session bean

* JVM-enként egy példány
* Használható inicializálásra
  * Induljon el az alkalmazással együtt: `@Startup` annotációval
* Megadható sorrend
  * `@DependsOn` annotációval
* Használható állapot megosztásra
  * Pl. cache, attribútumként

---

## Párhuzamosság vezérlése

* Container-managed concurrency (CMC)
* Bean-managed concurrency (BMC)

---

## Container-managed concurrency

* `@Lock(LockType.READ)`
* `@Lock(LockType.WRITE)`
* `@AccessTimeout(value = 20, unit = TimeUnit.SECONDS)`

---

## Bean-managed concurrency

* `@ConcurrencyManagement(ConcurrencyManagementType.BEAN)`
* `synchronized` kulcsszó használata

---

## Access timeout

* `0` érték esetén a metódus nem hívható párhuzamosan

---
class: inverse, center, middle


## Callback metódusok

---

## Callback metódusok

* Beanek életciklusához köthető eseményekkor
  * `@PostConstruct`
  * `@PrePassivate`
  * `@PostActivate`
  * `@PreDestroy`
* Metódus annotációval
  * Visszatérési érték `void`
  * Metódus neve szabadon választható
  * Paraméter nélkül
  * Csak nem ellenőrzött kivételt dobhat

---

## Stateless és singleton session bean életciklus

![Stateless és singleton session bean életciklus](images/ejbcon-statelesssblifecycle.gif)

---

## Stateful session bean életciklus

![Stateful session bean életciklus](images/ejbcon-statefulsblifecycle.gif)

---
class: inverse, center, middle


## Timer Service

---

## Timer Service

* Ütemező mechanizmus
  * Adott időpillanatban
  * Adott idő leteltével
  * Ismétlődés bizonyos időközönként
* Megadási módja
  * Deklaratív módon `@Schedule` annotációval
  * Programozott módon
* Használható un. _calendar based_ expression

---

## Deklaratív ütemezés

```java
public class EmployeeServiceBean {

  @Schedule(hour = "*", minute = "*", second = "*/10")
  public void sendNotificationMails() {
    // ...
  }

}
```

* Minden nap, minden óra, minden perc minden 10. másodpercében
* [Java EE tutorial/Creating Calendar-Based Timer Expressions](https://javaee.github.io/tutorial/ejb-basicexamples005.html#BNBOY)

---

## Programozott ütemezés

```java
public class EmployeeServiceBean {

  @Resource
  TimerService timerService;

  public void createEmployee(String name, LocalDate birthDay) {
      ScheduleExpression expression = new ScheduleExpression().
        dayOfMonth(birthDay.getDayOfMonth()).month(birthDay.getMonth());
      Employee employee = new Employee(name, birthDay);
      timerService.createCalendarTimer(expression, new TimerConfig(employee, true));
  }

  @Timeout
  public void sendBirthdayEmail(Timer timer) {
    Employee employee = (Employee) timer.getInfo();
    // ...
  }
}
```

* `TimerConfig`: info, perzisztencia

---

## További metódusok

* `createSingleActionTimer(java.util.Date, TimerConfig)`: adott időpontban
* `createSingleActionTimer(long, TimerConfig)`: adott idő múlva
* `createIntervalTimer(java.util.Date, long, TimerConfig)`: adott időponttól bizonyos időközönként
* `createIntervalTimer(long, long, TimerConfig)`: adott idő múlva bizonyos időközönként

---

## Ütemezések lekérdezése

* `TimerService.getTimers()`
* `Timer` interfész
  * `long getTimeRemaining()`
  * `java.util.Date getNextTimeout()`
  * `java.io.Serializable getInfo()`
  * `ScheduleExpression getSchedule()`
  * `TimerHandle getHandle()`
      * szerializálható
      * `Timer getTimer(TimerHandle)`

---
class: inverse, center, middle


## JSF

---

## JSF koncepció

* Komponensek, események, Java beanek kérés/válasz helyett
* Model-View-Controller (MVC) tervezési minta
* Könnyen tanulható, egységes, szabványos

---

## JSF fogalmai

* Faces Servlet (Front Controller tervezési minta)
  * Opcionális konfiguráció a `faces-config.xml` fájlban, helyette annotációk
* Backing beans: komponensek kezelése, üzleti logika hívás és navigáció
* Oldalak és komponensek: JSF 2.0 óta Facelets javasolt
    * Facelet tags
    * Expression Language: komponens és <br /> backing bean kapcsolata

---

## Backing bean

* Controller komponens (elnevezési konvenció)
* ` @javax.inject.Named` annotáció
  * Paraméterezhető a `value` attribútummal (alapértelmezetten az osztály nevéből)
* Scope: pl. `@RequestScoped`
* `@javax.enterprise.inject.Model` = <br /> `@Named` + `@RequestScoped`

---

## Backing bean tartalma

* getter/setter az attribútumokhoz (property)
* metódusok a felhasználói műveletekhez
* Életciklushoz tartozó metódusok `@PostConstruct` és `@PreDestroy` metódusokkal

---

## Scope

* Application
* Session
  * `Serializable`
* View
* Request
* Flash
* Flow

Javasolt minél kisebb scope használata

---

## Integráció üzleti logika réteggel

* Bár az oldalak közvetlen is hozzáférhetnek az EJB-khez
* EJB-k backing beanbe dependency injectionnel (`@Inject` annotáció)

---

## Oldal

* `h:head` és `h:body` használata

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html">

<h:head>
  <title>Employees</title>
</h:head>
<h:body>
  <h1>Employees</h1>
</h:body>
</html>

```

---

## Táblázatok

* `h:dataTable`
* `h:column`

```xml
<h:dataTable value="#{employeesController.employees}" var="employee">
    <h:column>
        <f:facet name="header">
            Id
        </f:facet>
        #{employee.id}
    </h:column>

    <h:column>
        <f:facet name="header">
            Name
        </f:facet>
        #{employee.name}
    </h:column>
</h:dataTable>
```

---

## Controller

```java
@Model
public class EmployeesController {

    @Inject
    private EmployeesService employeesService;

    private List<Employee> employees;

    @PostConstruct
    public void listEmployees() {
        employees = employeesService.listEmployees();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

}
```


---
class: inverse, center, middle


## JSF űrlap kezelés

---

## Redirect after post

* Űrlap POST után átirányítás
* History működése
* Refresh működése
* Könyvjelzőzhetőség

---

## Oldal

* `h:form`
* Beviteli komponensek
  * Pl. `h:inputText`, `value` attribútum: backing bean bind
* `h:commandButton`
  * `value` attribútum: szöveg
  * `action` attribútum: backing bean metódus, <br /> paraméterezhető, nem EL esetén oldal neve
* `h:commandLink`

---

## Form példa

```xml
<h:form>
    <h:inputText value="#{employeesController.name}" />
    <h:commandButton value="Create employee" action="#{employeesController.addEmployee()}" >
    </h:commandButton>
</h:form>
```

---

## Backing bean

* Metódus, `String` visszatérési értékkel
  * Navigációs szabályok
  * Konkrét oldal neve
  * `null` esetén az oldalon marad
  * Átirányítás: `?faces-redirect=true`

---

## Backing bean példa

```java
@Model
public class EmployeesController {

    @Inject
    private EmployeesService employeesService;

    private String name;

    public String addEmployee() {
        employeesService.createEmployee(name);        
        return "employees.xhtml?faces-redirect=true";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

---

## Flash attribútum

Backing beanben:

```java
FacesContext.getCurrentInstance().getExternalContext().getFlash()
  .put("successMessage", "Employee has created");
```

Faceletben:

```xml
#{flash['successMessage']}
```

---
class: inverse, center, middle


## Bean Validation

---

## Bean Validation

* Szabványos megoldás validációra
  * Ember vagy számítógép által a rendszerbe bevitt adatok ellenőrzésére
* Ne réteghez legyen kötve, hanem az adatot hordozó beanhez
* Megadható metódus paraméterekre és visszatérési értékre is

---

## Annotációk használata

```java
public class Employee {

  @NotBlank
  @Size(max = 200)
  private String name;

  @Min(100_000)
  private int salary;
}
```

---

## Beépített annotációk

* `@AssertFalse`, `@AssertTrue`
* `@Null`, `@NotNull`
* `@Size`
* `@Max`, `@Min`, `@Positive`, `@PositiveOrZero`, `@Negative`, `@NegativeOrZero`
* `@DecimalMax`, `@DecimalMin`
* `@Digits`

---

## Beépített annotációk

* `@Future`, `@Past`, `@PastOrPresent`, `@FutureOrPresent`
* `@Pattern`
* `@Email`
* `@NotEmpty`, `@NotBlank`

---

## Validáció futtatása

```java
ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
Validator validator = validatorFactory.getValidator();
Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
```

`ConstraintViolation` metódusai:

* `getPropertyPath()`
* `getInvalidValue()`
* `violation.getMessageTemplate()`

```
{javax.validation.constraints.NotBlank.message}
```

---

## Validáció CDI esetén

`Validator` példány injektálható

```java
@Named
public class EmployeeDao {

    @Inject
    private Validator validator;

    // ...
}
```

---

## Konstruktor, metódus paraméter és visszatérési érték

```java
private class EmployeeBean {

  @Positive
  public @Min(1) int createEmployee(@NotBlank @Size(max = 200) String name,
                                    @Min(100_000) int salary) {
    // ...
  }
}
```

* Háttérben `ExecutableValidator` példány, de ne használjuk közvetlenül, mindig
bízzuk valamilyen keretrendszerre, <br /> mint pl. a CDI

---
class: inverse, center, middle


## Saját validáció implementálása

---

## Validáció kompozíció

```java
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@NotNull
@Pattern(regexp = "[0-9]*")
public @interface ZipCode {

    String message() default "{zipcode.invalid_format}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

---

## Kötelező metódusok

* `message()` - hibaüzenet kulcsa
* `groups()` - csoportok, különböző helyzetekben más validációs szabályok
* `payload()` - metaadat, paraméterezhetőség

---

## Saját validáció - annotáció

```java
@Constraint(validatedBy = DivByValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface DivBy {

    String message() default "{div_by.invalid_value}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int number() default 10;
}
```

---

## Saját validáció - implementáló osztály

```java
public class DivByValidator implements ConstraintValidator<DivBy, Integer> {

    private int number;

    @Override
    public void initialize(DivBy constraintAnnotation) {
        number = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value.intValue() % number == 0;
    }
}
```

---

## Osztály szintű validáció

```java
@CountryAndZipValidation
public class Employee {

  private String country;

  private String zip;
}
```

* Pl. attribútumok összefüggésére

---

## Osztály szintű validáció - annotáció

```java
@Constraint(validatedBy = CountryAndZipValidator.class)
@Target({TYPE, METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface CountryAndZip {

    String message() default "{country_and_zip.illegal_zip_for_country}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
```

---

##  Osztály szintű validáció - implementáló osztály

```java
public class CountryAndZipValidator implements ConstraintValidator<CountryAndZip, Employee> {

    @Override
    public boolean isValid(Employee value, ConstraintValidatorContext context) {
        return !value.getCountry().equals("HU") ||
                value.getZip().matches("[0-9]{4}");
    }
}
```

---
class: inverse, center, middle

## Bean validation internationalization

---

## Internationalization

* Interfész `message` metódusa alapján
* Értéke legyen a kulcs

```java
@Constraint(validatedBy = DivByValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface DivBy {

    String message() default "{div_by.invalid_value}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int number() default 10;
}
```

---

## Használata

```java
public class Employee {
    // ...

    @DivBy
    private int salary;
}
```

```java
public class Employee {
    // ...

    @DivBy(number = 5, message = "Nem osztható")
    private int salary;
}
```

```java
public class Employee {
    // ...

    @DivBy(number = 5, message = "{employee_salary.invalid_value}")
    private int salary;
}
```

---

## Feloldás

* `ValidationMessages.properties` állomány alapján
* Feloldás operációs rendszer nyelve alapján (`ResourceBundle`)
* Keretrendszer figyelembe veheti a `Locale`-t saját `MessageInterpolator` alapján
* Egyébként saját `MessageInterpolator` implementálása

---

## Nyelvi állományok

`ValidationMessages_en_US.properties`:

```
div_by.invalid_value = Can not div
```

`ValidationMessages_hu_HU.properties`:

```
div_by.invalid_value = Nem osztható
```

---

## MessageInterpolator

```java
public class LocaleBasedMessageInterpolator implements MessageInterpolator {

    private final MessageInterpolator targetInterpolator;

    private final Locale locale;

    // Konstruktor

    @Override
    public String interpolate(String messageTemplate,
       Context context) {
        return targetInterpolator
            .interpolate(messageTemplate, context, locale);
    }

    @Override
    public String interpolate(String messageTemplate,
            Context context, Locale locale) {
        return targetInterpolator
            .interpolate(messageTemplate, context, locale);
    }
}
```

---

## MessageInterpolator megadása

```java
ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
Validator validator = validatorFactory.usingContext()
  .messageInterpolator(
    new LocaleBasedMessageInterpolator(
      validatorFactory.getMessageInterpolator(), new Locale("hu", "HU")))
  .getValidator();
Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
```

---

## Paraméterezése

`ValidationMessages_hu_HU.properties`:

```
div_by.invalid_value = Nem osztható {number} számmal
```

---
class: inverse, center, middle

## Validation Group

---

## Validation Group

* Ugyanazon osztályon, konstruktoron, metóduson, attribútumon különböző validációk
különböző esetekben
* Pl. ugyanazt az osztályt használjuk létrehozáskor és módosításkor is, az első
esetben nem kell azonosítót megadni
* Alapesetben minden a `Default` csoportban

---

## Annotációk

```java
public class EmployeeCommand {

    @NotNull(groups = ModifyEmployeeGroup.class)
    private Long id;

    private String name;

    // ...
}
```

Validation Group:

```java
public interface ModifyEmployeeGroup {
}
```

---

## Ellenőrzés

```java
ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
Validator validator = validatorFactory.getValidator();
Set<ConstraintViolation<EmployeeCommand>> violations =
    validator.validate(new EmployeeCommand(1L, "John Doe"),
    ModifyEmployeeGroup.class);
```

---

## Validation Group JSF-ben

* JSF-ben az űrlap definíciónál

```xml
<h:form>
    <h:inputText value="#{employeesController.command.id}">
        <f:validateBean validationGroups="ModifyEmployeeGroup.class" />
    </h:inputText>
    <h:inputText value="#{employeesController.command.name}" />
    <h:commandButton value="Modify employee" action="#{employeesController.modifyEmployee()}" />
</h:form>
```

---

## Validációk sorrendje

* A validációk futásának sorrendje nem definiált
* Validációk több részre bontása, ha az egyik elbukik, tovább ne folytatódjon
  * Pl. először az egyszerűek, ha azon megbukik, már ne folytassa a bonyolultabbakan
* Group Sequence
* Ha az egyikhez tartozó validációkon elbukik, <br /> nem folytatja a következőn

```java
@GroupSequence({SimpleEmployeeValidationGroup.class,
  ExtendedEmployeeValidationGroup.class})
```

---
class: inverse, center, middle

## Bean Validation Java EE környezetben

---

## Bean Validation Java EE környezetben

* Injektálható: `ValidatorFactory`, `Validator`
* JPA entitásra
* EJB metódus paraméterekre és visszatérési értékre
* JSF model attribútumokra
* JSF model attribútumként szereplő osztályra

---
class: inverse, center, middle

## Naplózás
---

## Naplózás

* Rendszerben történő események, rendszer állapota
* Monitorozás, hibakeresés, audit
* Éles környezetben, ahol a debugger nem elérhető
* `System.out.println` helyett

---

## Naplózó keretrendszerek

* Apache Commons Logging API
* Log4j
* Log4j 2
* Logback
* `java.util.logging` - JUL
* SLF4J

---

## Jellemzők

* Különböző modulokhoz/komponensekhez különböző naplózási szintek
* Naplózási szintek (trace, debug, info, error)
* Forráskód módosítása nélkül, konfigurációs állományban beállítható
* Több cél (pl. fájl, adatbázis, stb.)
* Több formátum

---

## Java EE naplózás

* Java EE nem ad szabványos támogatást
* Egy alkalmazásszerveren több alkalmazás
* Adminisztrátor által módosítható legyen, alkalmazás módosítása nélkül
  * Vagy alkalmazásszerver függő, így standard adminisztrációs eszközökkel karbantartható legyen
  * Vagy alkalmazás kívülről olvasson be egy fájlt

---

## Javasolt megoldás

* SLF4J alkalmazása
* Alkalmazásszerver támogatás megvizsgálása

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
```

```java
private final Logger logger =
	LoggerFactory.getLogger(this.getClass());
```

```java
logger.info("Employee has created with id {}", employee.getId());
```

---

## WildFly támogatás

* Globális konfiguráció: logging subsystem
* SLF4J függőség adott, `provided` scope
* Log4j implementáció
* Konzolra írást is átirányítja a Log4j-nek

---

## Platformfüggetlen megoldás

* Alkalmazásszerver konfigurálása, hogy ne avatkozzon bele
* SLF4J függőség
* Implementáció függőség, pl. Logback
* Annak implementálása, hogy kerüljön a fájlrendszerben tárolt külső
  konfigurációs állomány

---
class: inverse, center, middle

# SOAP webszolgáltatások JAX-WS szabvánnyal

---

## Webszolgáltatás

* W3C definíció: hálózaton keresztüli gép-gép együttműködést támogató
szoftverrendszer
* Platform független
* Szereplők
    * Szolgáltatást nyújtó
    * Szolgáltatást használni kívánó
    * Registry

---

## SOAP alapú webszolgáltatások

* Szabványokon alapuló
    * SOAP
    * WSDL
    * UDDI
* Kapcsolódó szabványok
    * HTTP
    * XML, és kapcsolódó szabványok: XSD, névterek

---

## SOAP

* W3C által karbantartott szabvány

![SOAP üzenet](images/soap-message.gif)
---

## Példa SOAP kérés

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Header/>
   <soap:Body>
      <listEmployees xmlns="http://training360.com/empapp"/>
   </soap:Body>
</soap:Envelope>
```

---

## Példa SOAP válasz

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <listEmployeesResponse xmlns="http://training360.com/empapp">
         <employee>
            <id>1</id>
            <name>John Doe</name>
         </employee>
         <employee>
            <id>2</id>
            <name>Jane Doe</name>
         </employee>
      </listEmployeesResponse>
   </soap:Body>
</soap:Envelope>
```

---

## WSDL

* Web Services Description Language
* WSDL dokumentum: több állományból

---

## WSDL felépítése

![WSDL](images/wsdl.gif)

---

## Példa WSDL állomány

```xml
<?xml version='1.0' encoding='UTF-8'?>
<wsdl:definitions xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
	xmlns:tns="http://training360.com/empapp"
	xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
	xmlns:ns1="http://schemas.xmlsoap.org/soap/http"
	name="EmployeeEndpointService"
	targetNamespace="http://training360.com/empapp">

    <!-- ... -->

</wsdl:definitions>
```

---

## Példa WSDL állomány

```xml
<wsdl:types>
    <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns:tns="http://training360.com/empapp"
        elementFormDefault="qualified"
        targetNamespace="http://training360.com/empapp" version="1.0">

        <xs:element name="listEmployees" type="tns:listEmployees"/>

        <xs:element name="listEmployeesResponse" type="tns:listEmployeesResponse"/>

        <xs:complexType name="listEmployees">
          <xs:sequence/>
        </xs:complexType>

        <!-- ... -->

    </xs:schema>
</wsdl:types>
```

---

## Példa WSDL állomány

```xml
<wsdl:message name="listEmployees">
  <wsdl:part element="tns:listEmployees" name="parameters">
  </wsdl:part>
</wsdl:message>
<wsdl:message name="listEmployeesResponse">
  <wsdl:part element="tns:listEmployeesResponse" name="parameters">
  </wsdl:part>
</wsdl:message>
<wsdl:portType name="EmployeeEndpoint">
  <wsdl:operation name="listEmployees">
    <wsdl:input message="tns:listEmployees" name="listEmployees">
  </wsdl:input>
    <wsdl:output message="tns:listEmployeesResponse" 
        name="listEmployeesResponse">
  </wsdl:output>
  </wsdl:operation>
</wsdl:portType>
```

---

## Példa WSDL állomány

```xml
<wsdl:binding name="EmployeeEndpointServiceSoapBinding" type="tns:EmployeeEndpoint">
  <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
  <wsdl:operation name="listEmployees">
    <soap:operation soapAction="" style="document"/>
    <wsdl:input name="listEmployees">
      <soap:body use="literal"/>
    </wsdl:input>
    <wsdl:output name="listEmployeesResponse">
      <soap:body use="literal"/>
    </wsdl:output>
  </wsdl:operation>
</wsdl:binding>
<wsdl:service name="EmployeeEndpointService">
  <wsdl:port binding="tns:EmployeeEndpointServiceSoapBinding" 
    name="EmployeeEndpointPort">
    <soap:address 
         location="http://localhost:8080/empapp/EmployeeEndpoint"/>
  </wsdl:port>
</wsdl:service>
```

---

## Implementációs megközelítések

* Megközelítések
    * Bottom up: kód alapján
    * Top down: WSDL alapján
    * Meet in the middle
* Üzleti logika != interfész
    * Két szolgáltatás, két osztály
    * Megoldás: wrapper réteg

---

## JAX-WS

* Szabvány SOAP webszolgáltatások és kliensek fejlesztésére
* Java SE-ben is használható
* Java EE-ben
    * Web alkalmazásban: POJO (nem kell EJB konténer)
    * EJB: Stateless session bean (biztonság, tranzakció-kezelés)
* Épít a JAXB-re
* Támogatja a kód és WSDL alapú megközelítést is

---

## Implementációk

* JAX-WS Reference Implementation
    * https://javaee.github.io/metro-jax-ws/
* Apache Axis2
    * http://axis.apache.org/axis2/java/core/
* CXF
    * http://cxf.apache.org/

---

## Endpoint implementáció

```java
@Stateless
@WebService(targetNamespace = "http://training360.com/empapp")
public class EmployeeEndpoint {

    @WebMethod
    @WebResult(name = "employee")
    @ResponseWrapper(localName = "employees")
    public List<Employee> listEmployees() {
        // ...
    }
}
```

---

## SOAPUi

* SOAP és RESTful webszolgáltatások tesztelésére
* Ingyenes és kereskedelmi verzió
* Webszolgáltatás hívás
* Teszt csomagok, tesztesetek, asserttek
* Szimulálás és mockolás
* Terheléses tesztelés
* Futtatás parancssorból

---
class: inverse, center, middle

# RESTful webszolgáltatások JAX-RS szabvánnyal

---

## RESTful webszolgáltatások

* Roy Fielding: Architectural Styles and the Design of Network-based Software Architectures, 2000
* Representational state transfer
* Egyedileg címezhető erőforrások (resource)
* Erőforrások különböző formátumokban elérhetőek
* Erőforrásokon végzett standard (CRUD) műveletek
* Állapotmentes (stateless)

---

## RESTful webszolgáltatások

* Létező technológiák: URI, HTTP, XML, JSON
* AJAX világ segítette az elterjedését
* Web Application Description Language (WADL) – nem elterjedt

---

## HTTP újrafelhasználása

* URL
* HTTP metódusok használata (GET, PUT, POST, DELETE)
* Mime-type
* URL paraméterek
* Fejlécek
* Státuszkódok

---

## JAX-RS


* Java EE része, Java SE-ben is használható
* Root resource classes: POJO, EJB
* Resource methods: URI & operation to method mapping

---

## Deployment

```java
@ApplicationPath("api")
public class RestConfig extends Application
{
}
```

---

## JAX-RS annotációk

* `@Path`
* `@Produces`
* `@Consumes`
* `@GET`, `@POST`, `@PUT`, `@DELETE`, `@HEAD` és `@OPTIONS`, `@HttpMethod`
* `@PathParam`, `@QueryParam`, `@MatrixParam`,
`@CookieParam`, `@HeaderParam` és `@FormParam`

---

## Resource

```java
@Path("employees")
public class EmployeeResource {

  @Inject
  private EmployeeServiceBean employeeServiceBean;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Employee> listEmployees() {
      return employeeServiceBean.findEmployees();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Employee findEmployeeById(@PathParam long id) {
      return employeeServiceBean.findEmployeeById(id);
  }

}
```

---

## Resource

```java
@Path("employees")
public class EmployeeResource {

  // ...

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveEmployee(Employee employeeData) {
      return Response
        .status(201)
        .entity(employeeServiceBean.saveEmployee(employeeData.getName()))
        .build();
  }

}
```

---

## Postman

* API fejlesztési életciklus támogatás
* RESTful webszolgáltatás hívások
* Egyszerre több környezet támogatása
* JavaScripttel szkriptelhető
* Automatikus tesztesetek
* Mock server
* Dokumentáció generálás
* Tesztesetek futtatása parancssorban

---
class: inverse, center, middle

# Java Message Service

---

## Message Oriented Middleware

* Rendszerek közötti üzenetküldés
* Megbízható üzenetküldés: store and forward
* Következő esetekben alkalmazható hatékonyan
  * Hívott fél megbízhatatlan
  * Kommunikációs csatorna megbízhatatlan
  * Hívott fél lassan válaszol
  * Terheléselosztás
  * Heterogén rendszerek
* Lazán kapcsolt rendszerek: nem kell ismerni a <br /> címzettet

---

## Point to point

![Point to point](images/jms-ptp.png)

---

## Publish and subscribe

<img src="images/jms-pas.png" alt="Publish and subscribe" width="600"/>

---

## JMS

* Szabványos Java API MOM-ekhez való hozzáféréshez
* Java EE része, de Java SE-ben is használható
* JMS provider
  * IBM MQ, Apache ActiveMQ, RabbitMQ
* Hozzáférés JMS API-n keresztül

---

## Destination

* Az üzenet küldésének célja
* Az üzenet fogadásának forrása
* Point to point környezetben: sor (queue)
* Publish and subscribe környezetben: téma (topic)
* JNDI vagy dependency injection

---

## Üzenet küldése

```java
@Stateless
public class MessageSender {

  @Inject
  private JMSContext context;

  @Resource(mappedName = "java:/jms/queue/EmployeeQueue")
  private Queue queue;

  public void sendMessage(String name) {
    context.createProducer().send(queue, name);
  }  
}
```

---

## Message

* `javax.jms.TextMessage extends javax.jms.Message`
* További interfészek: `BytesMessages`, `MapMessage`, `ObjectMessage`, `StreamMessage`
* Factory metódusok: pl. `createTextMessage(String text)`, stb.

---

## Message Driven Bean

* EJB üzenetek fogadására
* Állapotmentes, a konténer hívja
* Esemény alapú (aszinkron) – üzenet beérkezés
* `@MessageDriven` annotation
* `MessageListener` interfész `onMessage` metódusa
* Tipikusan típuskényszerítés a megfelelő üzenet <br /> típusra, majd delegálás session beanhez

---

## Üzenetfogadás MDB-vel

```java
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "java:/jms/queue/EmployeeQueue"
        )
})
public class EmployeesMessageDrivenBean implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("Message has arrived: " + 
                    textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
```

---

##WildFly támogatás

* `full` konfigurációval kell indítani, ekkor aktiválódik a JMS

```
$ standalone.bat -c=standalone-full.xml
```

* Adminisztrációs felületen (cli/webes) sor felvétele

```
$ jboss-cli.bat --connect

/subsystem=messaging-activemq/server=default/jms-queue=\
  EmployeeQueue:add(entries=[java:/jms/queue/EmployeeQueue])

/subsystem=messaging-activemq/server=default/address-setting=\
  jms.queue.EmployeeQueue:add(redelivery-delay=1500, \
  redelivery-multiplier=1.5, \
  max-redelivery-delay=5000, max-delivery-attempts=-1)
```

---
class: inverse, center, middle

## Aszinkron hívások

---

## Aszinkron hívás

```java
@Stateless
public class CalculatorBean {

  @Resource
  SessionContext ctx;

  @Asynchronous
  public Future<Optional<Integer>> calculateTotalSum() {
    int sum = 0;
    while (!hasCompleted()) {
      if (ctx.wasCancelCalled()) {
        return new AsyncResult<>(Optional.empty());
      }
      sum += doCalculate();
    }
    return new AsyncResult<>(sum);
  }
}
```
