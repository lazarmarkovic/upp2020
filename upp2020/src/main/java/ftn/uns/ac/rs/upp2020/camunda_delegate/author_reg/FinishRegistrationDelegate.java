package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FinishRegistrationDelegate implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("--- Task: finish registration delegate");

        String username = (String) delegateExecution.getVariable("username");

        User user = userService.findByUsername(username);
        user.setApproved(true);
    }
}
