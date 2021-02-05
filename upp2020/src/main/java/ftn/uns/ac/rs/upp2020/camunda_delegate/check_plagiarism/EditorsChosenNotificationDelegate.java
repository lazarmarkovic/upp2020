package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EditorsChosenNotificationDelegate implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void execute(DelegateExecution execution) throws Exception {

        ArrayList<User> chosenEditorsUsers = (ArrayList<User>) execution.getVariable("chosenEditorsUsers");

        for(User editor : chosenEditorsUsers){

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(editor.getEmail());
            mailMessage.setSubject("You have been chosen to review new plagiarism appeal");
            mailMessage.setText(String.format("%s, new plagiarism appeal has been made. " +
                    "Please leave your review.", editor.getId()));

            javaMailSender.send(mailMessage);

            System.out.println("Izabrani editori obavesteni");
        }
    }

}
