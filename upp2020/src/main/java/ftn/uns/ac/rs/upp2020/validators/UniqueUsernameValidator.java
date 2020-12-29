package ftn.uns.ac.rs.upp2020.validators;

import ftn.uns.ac.rs.upp2020.service.UserService;
import ftn.uns.ac.rs.upp2020.util.ServiceProvider;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;


public class UniqueUsernameValidator implements FormFieldValidator {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public boolean validate(Object object, FormFieldValidatorContext formFieldValidatorContext) {
        userService = ServiceProvider.getUserService();
        return userService.isUniqueUsername(object.toString());
    }
}
