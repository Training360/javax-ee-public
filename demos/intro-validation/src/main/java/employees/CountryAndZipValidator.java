package employees;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryAndZipValidator implements ConstraintValidator<CountryAndZip, Employee> {

    @Override
    public boolean isValid(Employee value, ConstraintValidatorContext context) {
        return !value.getCountry().equals("HU") ||
                value.getZip().matches("[0-9]{4}");
    }
}
