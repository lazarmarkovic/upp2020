package ftn.uns.ac.rs.upp2020.validators;

import ftn.uns.ac.rs.upp2020.service.UserServiceImpl;
import ftn.uns.ac.rs.upp2020.util.ServiceProvider;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueEmailValidator implements FormFieldValidator {

    @Autowired
    UserServiceImpl userService;

    @Override
    public boolean validate(Object object, FormFieldValidatorContext formFieldValidatorContext) {
        userService = ServiceProvider.getUserService();
        return userService.isUniqueEmail(object.toString());
    }
}
