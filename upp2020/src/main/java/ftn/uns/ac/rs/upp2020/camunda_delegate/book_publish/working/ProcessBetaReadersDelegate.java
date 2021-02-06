package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish.working;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessBetaReadersDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> betaReaders = (List<String>) delegateExecution.getVariable("betaReadersSelect");

        if (betaReaders.size() == 0) {
            delegateExecution.setVariable("useBetaReaders", false);
        } else {
            delegateExecution.setVariable("useBetaReaders", true);
            delegateExecution.setVariable("betaReaderUsernames", betaReaders);
        }
    }
}
