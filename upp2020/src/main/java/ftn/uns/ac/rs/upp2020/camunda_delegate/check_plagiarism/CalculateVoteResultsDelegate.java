package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CalculateVoteResultsDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Svi clanovi odbora glasali");
        ArrayList<Boolean> votes = (ArrayList<Boolean>) execution.getVariable("committeeVotes");

        if (votes.contains(true))
            if (votes.contains(false))
                execution.setVariable("plagiarismConclusion", "voteAgain");
            else
                execution.setVariable("plagiarismConclusion", "isPlagiarism");
        else
            execution.setVariable("plagiarismConclusion", "notPlagiarism");
    }
}
