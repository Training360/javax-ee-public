package employees;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Named
public class EmployeeService {

    private EmployeeDao employeeDao;

    @Inject
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public int saveEmployee(String name, int salary, String country, String zip) {
        employeeDao.saveEmployee(new Employee(name, salary, country, zip));
        return 0;
    }

    public List<Employee> listEmployees() {
        return employeeDao.listEmployees();
    }
}
