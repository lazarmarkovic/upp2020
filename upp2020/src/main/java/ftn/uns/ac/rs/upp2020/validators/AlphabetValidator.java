package ftn.uns.ac.rs.upp2020.validators;

import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class AlphabetValidator implements FormFieldValidator {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean validate(Object object, FormFieldValidatorContext formFieldValidatorContext) {

        return ((String)object).matches("[A-Z][a-zA-Z]*");
    }
}
