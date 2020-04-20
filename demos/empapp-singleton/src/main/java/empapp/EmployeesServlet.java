package empapp;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {

    @Inject
    private EmployeeServiceBean employeeService;

    @Inject
    private CounterBean counterBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        counterBean.inc();
        List<Employee> employees = employeeService.findEmployees();
        req.setAttribute("employees", employees);
        req.setAttribute("counter", counterBean.get());
        req.getRequestDispatcher("/WEB-INF/jsp/employees.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
            employeeService.saveEmployee(name);
        resp.sendRedirect("employees");
    }
}
