package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubstitutionPreparationDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        ArrayList<User> unresponsiveEditors = (ArrayList<User>) execution.getVariable("chosenEditorsUsers");
        ArrayList<User> availableEditors = (ArrayList<User>) execution.getVariable("availableEditors");

        execution.setVariable("numberOfSubstitutions", unresponsiveEditors.size());

        for (User editor: unresponsiveEditors){
            System.out.printf("%s je unresponsive%n", editor.getId());
            availableEditors.removeIf(e -> e.getId().equals(editor.getId()));
        }


        execution.setVariable("availableEditors", availableEditors);

        if(availableEditors.size() == 0){
            execution.setVariable("editorsEmpty", true);
        }
    }
}
