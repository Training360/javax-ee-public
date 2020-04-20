package empapp;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
public class EmployeeBean {

    @Inject
    private NameTrimmer nameTrimmer;

    @Resource(mappedName = "java:/jdbc/EmployeeDS")
    private DataSource dataSource;

    public List<Employee> findEmployees() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement("select id, emp_name from employees");
             ResultSet rs = st.executeQuery()
        ) {
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("emp_name");
                Employee employee = new Employee(id, name);
                employees.add(employee);
            }
            return employees;
        }
        catch (SQLException se) {
            throw new IllegalStateException("Can not query employees", se);
        }
    }

    public void saveEmployee(String name) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement("insert into employees(emp_name) values (?)");
        ) {
            st.setString(1, name);
            st.executeUpdate();
        }
        catch (SQLException se) {
            throw new IllegalStateException("Can not query employees", se);
        }
    }
}
