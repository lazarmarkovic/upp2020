package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetHeadEditorDelegate implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        User headEditor = identityService.createUserQuery().memberOfGroup("headEditor").list().get(0);

        execution.setVariable("headEditorUser", headEditor);
        execution.setVariable("headEditor", headEditor.getId());
    }
}
