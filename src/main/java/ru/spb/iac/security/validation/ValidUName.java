package ru.spb.iac.security.validation;

import javax.validation.*;
import java.lang.annotation.*;

/**
 * Created by manaev on 30.12.14.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=UserNameValidator.class)
@Documented
public @interface ValidUName {
    String message() default "Use only latin characters and numbers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
