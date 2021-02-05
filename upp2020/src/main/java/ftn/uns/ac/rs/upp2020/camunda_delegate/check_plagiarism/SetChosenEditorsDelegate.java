package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;


import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SetChosenEditorsDelegate implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        ArrayList<User> chosenEditorsUsers = new ArrayList<>();
        ArrayList<String> chosenEditors = new ArrayList<>();

        //TODO change iteration once editors string is replaced with custom type
        for(String editor : execution.getVariable("editors").toString().split("\\|")) {
            System.out.println(editor);

            User user = identityService.createUserQuery().userId(editor).singleResult();
            chosenEditorsUsers.add(user);
            chosenEditors.add(user.getId());
        }

        execution.setVariable("chosenEditorsUsers", chosenEditorsUsers);
        execution.setVariable("chosenEditors", chosenEditors);
    }

}
