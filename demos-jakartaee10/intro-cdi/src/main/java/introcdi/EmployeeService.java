package introcdi;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
public class EmployeeService {

    private EmployeeDao employeeDao;

    @Inject
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void saveEmployee(String name) {
        String trimmedName = name.trim();
        employeeDao.saveEmployee(trimmedName);
    }

    public List<String> listEmployees() {
        return employeeDao.listEmployees();
    }
}
