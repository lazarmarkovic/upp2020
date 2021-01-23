package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecideCommitteeVoteDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> votes = (List<String>) delegateExecution.getVariable("committeeVotes");
        int committeeSize = (int) delegateExecution.getVariable("committeeSize");
        System.out.println(votes);

        int yes = (int) votes.stream().filter(v -> v.equals("YES")).count();
        int no =  (int) votes.stream().filter(v -> v.equals("NO")).count();
        int submitMore = (int) votes.stream().filter(v -> v.equals("SUBMIT_MORE_WORKS")).count();

        delegateExecution.removeVariable("committeeDecision");

        if (submitMore > 0) {
            delegateExecution.setVariable("committeeDecision", "SUBMIT_MORE_WORKS");
        }
        else if (yes == committeeSize) {
            delegateExecution.setVariable("committeeDecision", "YES");
        }
        else if (no >= committeeSize / 2) {
            delegateExecution.setVariable("committeeDecision", "NO");
        }
        else {
            delegateExecution.setVariable("committeeDecision", "VOTE_AGAIN");
        }

        System.out.println((String)delegateExecution.getVariable("committeeDecision"));

    }
}
