package employees;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeServiceTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    private SeContainer container;

    private EmployeeService employeeService;

    @Before
    public void initCdi() {
        container = SeContainerInitializer.newInstance().initialize();
        employeeService = container.select(EmployeeService.class).get();
    }

    @After
    public void destroyCdi() {
        container.close();
    }

    @Test
    public void testSaveAndList() {
        employeeService.saveEmployee("John Doe", 200_000, "HU", "2071");
        assertEquals("John Doe", employeeService.listEmployees().get(0).getName());
    }

    @Test
    public void testInvalid() {
        expected.expect(IllegalArgumentException.class);
        employeeService.saveEmployee("Jo", 200_000, "HU", "2071");

    }

}
