package introcdi;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeServiceIntegrationTest {

    @Test
    void saveAndList() {
        try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            EmployeeService employeeService = container.select(EmployeeService.class).get();
            employeeService.saveEmployee("John Doe");
            employeeService.saveEmployee("Jane Doe");
            assertEquals(List.of("John Doe", "Jane Doe"), employeeService.listEmployees());
        }
    }

}
