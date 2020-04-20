package employees;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class EmployeeCommandValidationTest {

    @Test
    public void testCreate() {
        EmployeeCommand employeeCommand = new EmployeeCommand("John Doe");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<EmployeeCommand>> violations =
                validator.validate(employeeCommand);
        assertEquals(0, violations.size());
    }

    @Test
    public void testModify() {
        EmployeeCommand employeeCommand = new EmployeeCommand("John Doe");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<EmployeeCommand>> violations =
                validator.validate(employeeCommand, ModifyEmployeeGroup.class);
        assertEquals(1, violations.size());
    }
}
