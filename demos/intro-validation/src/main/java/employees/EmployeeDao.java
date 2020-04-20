package employees;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Named
public class EmployeeDao {

    @Inject
    private Validator validator;

    private List<Employee> employees = Collections.synchronizedList(new ArrayList<>());

    public void saveEmployee(Employee employee) {
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Employee is not valid");
        }
        employees.add(employee);
    }

    public List<Employee> listEmployees() {
        return new ArrayList<>(employees);
    }
}
