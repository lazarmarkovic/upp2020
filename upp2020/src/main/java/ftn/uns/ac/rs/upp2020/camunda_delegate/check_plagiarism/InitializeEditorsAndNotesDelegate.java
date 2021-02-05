package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InitializeEditorsAndNotesDelegate implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        ArrayList<String> editorsNotes = new ArrayList<>();
        ArrayList<User> availableEditors = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("editors").list();

        execution.setVariable("availableEditors", availableEditors);
        execution.setVariable("editorsNotes", editorsNotes);
        execution.setVariable("numberOfSubstitutions", 0 );
        if (availableEditors.size() == 0)
            execution.setVariable("editorsEmpty", true);
        else
            execution.setVariable("editorsEmpty", false);
    }
}
