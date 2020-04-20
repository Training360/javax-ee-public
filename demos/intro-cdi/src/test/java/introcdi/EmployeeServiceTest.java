package introcdi;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class EmployeeServiceTest {

    private EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);

    private EmployeeService employeeService = new EmployeeService(employeeDao);

    @Test
    public void testSaveEmployee() {
        employeeService.saveEmployee("  John Doe  ");
        verify(employeeDao).saveEmployee("John Doe");
    }

}
