package employees;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmployeeValidationTest {

    @Test
    public void testValid() {
        Employee employee = new Employee("John Doe", 200_000, "HU", "2071");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNameIsBlank() {
        Employee employee = new Employee("Jo", 200_000, "HU", "2071");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.stream().findAny().get();
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("Jo", violation.getInvalidValue());
        assertEquals("{employee_name.invalid_value}", violation.getMessageTemplate());
        assertEquals("A név hossza nem megfelelő (3 - 200)!", violation.getMessage());
    }

    @Test
    public void testZipCodeInvalid() {
        Employee employee = new Employee("John Doe", 200_000, "EN", "2071A");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.stream().findAny().get();
        assertEquals("zip", violation.getPropertyPath().toString());
        assertEquals("2071A", violation.getInvalidValue());
        assertEquals("{javax.validation.constraints.Pattern.message}", violation.getMessageTemplate());
    }

    @Test
    public void testNotDivByTen() {
        Employee employee = new Employee("John Doe", 200_005, "HU", "2071");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.stream().findAny().get();
        assertEquals("salary", violation.getPropertyPath().toString());
        assertEquals(200_005, violation.getInvalidValue());
        assertEquals("{div_by.invalid_value}", violation.getMessageTemplate());
        assertEquals("Nem osztható 10 számmal!", violation.getMessage());
    }

    @Test
    public void testNotDivByTenEn() {
        Employee employee = new Employee("John Doe", 200_005, "HU", "2071");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.usingContext()
                .messageInterpolator(new LocalBasedMessageInterpolator(
                        validatorFactory.getMessageInterpolator(), Locale.US
                )).getValidator();
        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.stream().findAny().get();
        assertEquals("salary", violation.getPropertyPath().toString());
        assertEquals(200_005, violation.getInvalidValue());
        assertEquals("{div_by.invalid_value}", violation.getMessageTemplate());
        assertEquals("Can not div by 10!", violation.getMessage());
    }

    @Test
    public void testHuButZipIsLongerThanFour() {
        Employee employee = new Employee("John Doe", 200_000, "HU", "20711");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.stream().findAny().get();
        assertEquals(employee, violation.getInvalidValue());
        assertEquals("{country_and_zip.invalid_value}", violation.getMessageTemplate());
    }

}
