package ftn.uns.ac.rs.upp2020.camunda_delegate;

import java.util.Optional;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.upp2020.domain.Book;
import ftn.uns.ac.rs.upp2020.repository.BookRepository;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;

@Service
public class RejectionNotifierEmail implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired 
    UserRepository userRepository;

    @Autowired 
    BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String writer = (String) execution.getVariable("loggedInWriter");
        String explanation = (String) execution.getVariable("explanation_field");
        String email = writer + "@gmail.com";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Your publishment has been rejected.");
        mailMessage.setText("Your book has been rejected. Reason for your rejection is:" + explanation);

        javaMailSender.send(mailMessage);

    }
    
}
