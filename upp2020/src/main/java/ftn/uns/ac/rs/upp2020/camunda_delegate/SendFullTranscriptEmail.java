package ftn.uns.ac.rs.upp2020.camunda_delegate;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;

@Service
public class SendFullTranscriptEmail implements JavaDelegate{
    
    
    @Autowired 
    UserRepository userRepository;
    
    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("HERE");

        List<User> users = userRepository.findAllActiveUsers();

        System.out.println(users.size());

        // mailMessage.setTo();
        // mailMessage.setSubject("UPP account verification code");
        // mailMessage.setText( +  ",\n Please verify your account.\n Your activation code is: " + hashCode);

        // javaMailSender.send(mailMessage);
    }



}
