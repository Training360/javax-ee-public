package empapp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.stream.Collectors;

public class EmployeesMain {

    public static void main(String[] args) throws NamingException {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.URL_PKG_PREFIXES,
                "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.PROVIDER_URL,
                "http-remoting://localhost:8080");
        jndiProperties.put("jboss.naming.client.ejb.context", true);

        Context ctx = new InitialContext(jndiProperties);

        EmployeeServiceRemote employeeService =
                (EmployeeServiceRemote) ctx.lookup("empapp/EmployeeServiceBean!empapp.EmployeeServiceRemote");

        employeeService.saveEmployee("John Doe");
        employeeService.saveEmployee("Jane Doe");

        System.out.println(employeeService.findEmployees().stream().map(Employee::getName).collect(Collectors.toList()));

        employeeService.end();

        System.out.println(employeeService.findEmployees().stream().map(Employee::getName).collect(Collectors.toList()));
    }
}
