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
public class EditorChangesNotifier implements JavaDelegate{
    
    
    @Autowired 
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("CHANGES");

        String editor = (String) execution.getVariable("editor");
        String email = editor + "@gmail.com";


        System.out.println(email);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Here is fixed");
        mailMessage.setText("Fixed version of my transcript.");

        javaMailSender.send(mailMessage);
    }



}
