package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

@Getter
@Setter
class BankResponseDTO implements Serializable {
    private boolean approved;
}

@Service
public class AcceptMembershipFeesDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("ACCEPT PAYMENT");

        String username = (String) delegateExecution.getVariable("username");

        RestTemplate restTemplate = new RestTemplate();
        while (true) {
            System.out.println("TIK");
            Thread.sleep(10000);

            BankResponseDTO bankResponseDTO = restTemplate
                    .getForObject("http://localhost:5000/ok/" + username, BankResponseDTO.class);

            if (bankResponseDTO.isApproved()) {
                return;
            }

        }
    }
}
