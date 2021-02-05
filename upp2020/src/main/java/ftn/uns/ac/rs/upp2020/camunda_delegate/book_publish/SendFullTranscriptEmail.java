package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;

@Service
public class SendFullTranscriptEmail implements JavaDelegate{
    
    
    @Autowired 
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("HERE");

        String writer = (String) execution.getVariable("writer");
        String email = writer + "@gmail.com";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Please upload your full transcript");
        mailMessage.setText("Your application has been considered and we would like to your full transcript");

        javaMailSender.send(mailMessage);
    }



}
