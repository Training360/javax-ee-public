package empapp;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Model
public class EmployeesController {

    @Inject
    private EmployeeServiceBean employeeServiceBean;

    private List<Employee> employees;

    @NotBlank
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
