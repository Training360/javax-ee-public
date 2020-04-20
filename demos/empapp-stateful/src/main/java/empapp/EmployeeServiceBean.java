package empapp;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateful
public class EmployeeServiceBean implements EmployeeServiceRemote,
    EmployeeServiceLocal {

    private List<Employee> employees = new ArrayList<>();

    @Override
    public List<Employee> findEmployees() {
        return new ArrayList<>(employees);
    }

    @Override
    public void saveEmployee(String name) {
        employees.add(new Employee(name));
    }

    @Override
    @Remove
    public void end() {

    }
}
