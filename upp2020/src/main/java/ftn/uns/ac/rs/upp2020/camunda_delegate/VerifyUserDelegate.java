package ftn.uns.ac.rs.upp2020.camunda_delegate;


import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.exceptions.GeneralException;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class VerifyUserDelegate implements JavaDelegate {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("PROCESS VERIFICATION CODE");
        String username = (String) execution.getVariable("username");
        System.out.println("Verification user: " + username);

        String enteredCode = (String) execution.getVariable("code");
        System.out.println("Verification code: " + enteredCode);

        User u = userRepository.findByUsername(username);
        System.out.println(u.getVerificationCode());
        if(u.getVerificationCode().equals(enteredCode)){
            u.setVerified(true);
            userRepository.save(u);
        }else {
            throw new GeneralException("Verification code is wrong!");
        }

    }
}
