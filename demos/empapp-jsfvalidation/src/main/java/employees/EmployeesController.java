package employees;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Model
public class EmployeesController {

    @Inject
    private EmployeesDao employeesDao;

    private List<Employee> employees;

//    @NotBlank
//    private String name;

    private CreateEmployeeCommand command = new CreateEmployeeCommand();

    @PostConstruct
    public void listEmployees() {
        employees = employeesDao.listEmployees();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String addEmployee() {
        employeesDao.createEmployee(command.getName());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("successMessage", "Employee has created");
        return "employees.xhtml?faces-redirect=true";
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }


    public CreateEmployeeCommand getCommand() {
        return command;
    }

    public void setCommand(CreateEmployeeCommand command) {
        this.command = command;
    }
}
