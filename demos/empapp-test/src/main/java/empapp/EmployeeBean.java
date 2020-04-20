package empapp;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class EmployeeBean {

    @Inject
    private NameTrimmer nameTrimmer;

    private List<Employee> employees = Collections.synchronizedList(
            new ArrayList<>(List.of(new Employee("John Doe"),
                    new Employee("Jane Doe"))));

    public List<Employee> findEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void saveEmployee(String name) {
        String trimmedName = nameTrimmer.trimName(name);
        employees.add(new Employee(trimmedName));
    }
}
