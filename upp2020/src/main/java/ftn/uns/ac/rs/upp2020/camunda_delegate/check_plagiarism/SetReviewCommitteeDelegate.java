package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SetReviewCommitteeDelegate implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        ArrayList<User> committeeMembersUsers = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("committeeMembers").list();
        ArrayList<User> headCommitteeMembersUsers = (ArrayList<User>) identityService.createUserQuery().memberOfGroup("headCommitteeMembers").list();
        ArrayList<Boolean> committeeVotes = new ArrayList<>();

        committeeMembersUsers.addAll(headCommitteeMembersUsers);

        ArrayList<String> committeeMembers = new ArrayList<>();
        
        for (User cm : committeeMembersUsers)
            committeeMembers.add(cm.getId());


        execution.setVariable("committeeMembersUsers", committeeMembersUsers);
        execution.setVariable("committeeMembers", committeeMembers);
        execution.setVariable("committeeVotes", committeeVotes);
    }
}
