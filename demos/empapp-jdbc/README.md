## Séma és felhasználó létrehozása

use mysql;
create schema if not exists employees default character set utf8 collate utf8_hungarian_ci;
create user 'employees'@'localhost' identified by 'employees';
grant all on *.* to 'employees'@'localhost';
use employees;
create table employees (id bigint auto_increment, emp_name varchar(255),
      constraint pk_employee primary key (id));

## DataSource beállítása

A `jboss-cli.bat` állományt elindítva a parancssorba beírandó:

connect localhost
deploy "~/mysql-connector-java-8.0.12.jar"
/subsystem=datasources:installed-drivers-list
data-source add --name=EmployeeDS --jndi-name=java:/jdbc/EmployeeDS --driver-name=mariadb-java-client-2.3.0.jar --connection-url=jdbc:mysql://localhost/employees?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC --user-name=employees --password=employees
/subsystem=datasources:read-resource