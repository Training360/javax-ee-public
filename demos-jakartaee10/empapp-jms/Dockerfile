FROM quay.io/wildfly/wildfly:30.0.1.Final-jdk20
WORKDIR deploy
RUN ["curl", "-o", "mysql-connector-j-8.3.0.jar", "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar"]
COPY customization .
RUN ["./execute.sh", "standalone", "standalone-full.xml"]
COPY target/empapp.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-c", "standalone-full.xml"]