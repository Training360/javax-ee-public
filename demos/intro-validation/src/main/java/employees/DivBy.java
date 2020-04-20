package employees;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DivByValidator.class})
public @interface DivBy {

    String message() default "{div_by.invalid_value}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int number() default 10;
}
