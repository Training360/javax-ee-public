package empapp;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateful
public class EmployeeServiceBean {

    @Inject
    private EmployeeDaoBean employeeDaoBean;

    @Inject
    private LogEntryDaoBean logEntryDaoBean;

    public List<Employee> findEmployees() {
        return employeeDaoBean.findEmployees();
    }

    @Transactional
    public void saveEmployee(String name) {
        logEntryDaoBean.saveLogEntry("Save employee with name: " + name);

        employeeDaoBean.saveEmployee(name);
    }

}
