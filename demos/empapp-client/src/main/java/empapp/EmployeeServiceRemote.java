package empapp;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface EmployeeServiceRemote {
    List<Employee> findEmployees();

    void saveEmployee(String name);
}
