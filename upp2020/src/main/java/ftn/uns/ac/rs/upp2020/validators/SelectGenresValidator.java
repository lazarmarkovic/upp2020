package ftn.uns.ac.rs.upp2020.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.ArrayList;


public class SelectGenresValidator implements FormFieldValidator {
    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        return ((ArrayList<?>)o).size() > 0;
    }
}
