package empapp;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Stateless
public class EmployeeServiceBean {

    @Inject
    private EmployeeDaoBean employeeDaoBean;

    @Inject
    private LogEntryDaoBean logEntryDaoBean;

    public List<Employee> findEmployees() {
        return employeeDaoBean.findEmployees();
    }

    @Transactional
    public void saveEmployee(
 	        // @NotBlank
		String name) {
        logEntryDaoBean.saveLogEntry("Save employee with name: " + name);

        employeeDaoBean.saveEmployee(name);
    }

}
