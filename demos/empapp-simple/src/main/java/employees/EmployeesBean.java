package employees;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class EmployeesBean {

    private List<Employee> employees =
            Collections.synchronizedList(
                    new ArrayList<>(
                            List.of(new Employee("John Doe"),
                                    new Employee("Jane Doe"))));

    public List<Employee> listEmployees() {
        return new ArrayList<>(employees);
    }
}
