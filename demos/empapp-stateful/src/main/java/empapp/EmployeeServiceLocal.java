package empapp;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

@Local
public interface EmployeeServiceLocal {
    List<Employee> findEmployees();

    void saveEmployee(String name);
}
