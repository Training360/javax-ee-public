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

    private String name;

    @PostConstruct
    public void init() {
        employees = employeeServiceBean.findEmployees();
    }

    public String addEmployee() {
        employeeServiceBean.saveEmployee(name);
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().put("successMessage", "Employee has created!");
        return "employees.xhtml?faces-redirect=true";
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
