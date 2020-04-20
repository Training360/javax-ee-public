package introcdi;

import org.junit.Test;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeServiceIntegrationTest {

    @Test
    public void testSaveAndList() {
        try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            EmployeeService employeeService = container.select(EmployeeService.class).get();
            employeeService.saveEmployee("John Doe");
            employeeService.saveEmployee("Jane Doe");
            assertEquals(List.of("John Doe", "Jane Doe"), employeeService.listEmployees());
        }
    }

}
