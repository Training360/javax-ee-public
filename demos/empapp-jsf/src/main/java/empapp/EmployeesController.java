package empapp;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

@Model
public class EmployeesController {

    @Inject
    private EmployeeServiceBean employeeServiceBean;

    private List<Employee> employees;

    @PostConstruct
    public void init() {
        employees = employeeServiceBean.findEmployees();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

}
