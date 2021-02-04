package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class Logger implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Logger");
        System.out.println(execution.getVariable("editors").toString());
    }

}
