package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InitVotingRoundDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("--- Task: init voting round");

        delegateExecution.setVariable("committeeVotes", new ArrayList<String>());
        delegateExecution.setVariable("committeeComments", new ArrayList<String>());
    }
}
