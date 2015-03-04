package ru.spb.iac.security.validation;

import com.google.common.base.*;

import javax.validation.*;
import java.util.regex.*;

/**
 * Created by manaev on 30.12.14.
 */
public class UserNameValidator implements ConstraintValidator<ValidUName,String> {

    private Pattern pattern = Pattern.compile(
            "^[a-zA-Z\\d]+$", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(ValidUName validUName) {

    }
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !Strings.isNullOrEmpty(username) && pattern.matcher(username).matches();
    }
}
