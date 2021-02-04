package ftn.uns.ac.rs.upp2020.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.io.File;


public class FileUploadValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext validatorContext) {
        if(((File) submittedValue).length() == 0) return false;
        return true;
    }

}
