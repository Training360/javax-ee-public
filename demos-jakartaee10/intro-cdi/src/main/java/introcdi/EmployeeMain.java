package introcdi;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import java.util.List;

public class EmployeeMain {

    public static void main(String[] args) {
        try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            EmployeeService employeeService = container.select(EmployeeService.class).get();
            employeeService.saveEmployee("  John Doe  ");

            List<String> employees = employeeService.listEmployees();
            System.out.println(employees);
        }
    }
}
