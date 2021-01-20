package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ConfirmPayment implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CONFIRM PAYMENT");
        Boolean paymentOk = (Boolean) delegateExecution.getVariable("confirm");

        delegateExecution.setVariable("payment", paymentOk);
    }
}
