package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.exceptions.GeneralException;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProcessEmailConfirmationCodeDelegate implements JavaDelegate {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("--- Task: process email confirmation code");

        String confCode = execution.getVariable("confirmationEmailCode").toString();
        Optional<User> opt = userRepository.findByVerificationCode(confCode);

        if (opt.isPresent()){
            User user = opt.get();
            user.setVerified(true);
            userRepository.save(user);
        } else {
            throw new GeneralException("Verification code is wrong!");
        }
    }
}
