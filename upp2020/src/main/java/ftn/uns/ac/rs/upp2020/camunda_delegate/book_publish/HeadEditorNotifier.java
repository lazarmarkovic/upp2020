package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ftn.uns.ac.rs.upp2020.repository.UserRepository;

@Service
public class HeadEditorNotifier implements JavaDelegate{
    
    
    @Autowired 
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RuntimeService runtimeService;
    
    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("CHANGES");



        String headEditor = (String) execution.getVariable("headEditor");
        String email = headEditor + "@gmail.com";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Transcript review");
        mailMessage.setText("Please review this transcript.");

        javaMailSender.send(mailMessage);

        runtimeService.setVariable(execution.getProcessInstanceId(), "headEditor", "milos.pantic11996");
    }



}
