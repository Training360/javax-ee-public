package employees;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DivByValidator implements ConstraintValidator<DivBy, Integer> {

    private int number;

    @Override
    public void initialize(DivBy constraintAnnotation) {
        number = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value.intValue() % number == 0;
    }
}
