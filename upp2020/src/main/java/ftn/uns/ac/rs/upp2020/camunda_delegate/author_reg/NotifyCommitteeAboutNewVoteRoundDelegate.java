package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import ftn.uns.ac.rs.upp2020.domain.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotifyCommitteeAboutNewVoteRoundDelegate implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String firstName = (String) delegateExecution.getVariable("firstName");
        String lastName = (String) delegateExecution.getVariable("lastName");
        List<User> committee = (List<User>)delegateExecution.getVariable("committee");

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject("UPP Author membership review");
        mailMessage.setText("Author " + firstName + " " + lastName + " has submitted works for your review.");

        committee.forEach((c) -> {
            mailMessage.setTo(c.getEmail());
            javaMailSender.send(mailMessage);
        });
    }
}
