package empapp;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

@RunWith(Arquillian.class)
public class EmployeeBeanArqTest {

    @Inject
    private EmployeeBean employeeBean;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(Employee.class, NameTrimmer.class, EmployeeBean.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testSaveThenList() {
        employeeBean.saveEmployee("     John Doe XXX    ");
        List<Employee> employees = employeeBean.findEmployees();
        employees.stream()
                .filter(e -> e.getName().equals("John Doe XXX"))
                .findFirst().get();
    }

}
