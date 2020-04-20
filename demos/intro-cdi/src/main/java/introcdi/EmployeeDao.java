package introcdi;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named
public class EmployeeDao {

    private List<String> employees = Collections.synchronizedList(new ArrayList<>());

    public void saveEmployee(String name) {
        employees.add(name);
    }

    public List<String> listEmployees() {
        return new ArrayList<>(employees);
    }
}
