FROM quay.io/wildfly/wildfly:33.0.2.Final-jdk21
WORKDIR /deploy
RUN ["curl", "-o", "postgresql-42.7.4.jar", "https://repo.maven.apache.org/maven2/org/postgresql/postgresql/42.7.4/postgresql-42.7.4.jar"]
COPY customization .
RUN ["./execute.sh", "standalone", "standalone-full.xml"]
COPY target/empapp.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-c", "standalone-full.xml"]