package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class NotifyAuthorToSendMoreWorks implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String firstName = (String) delegateExecution.getVariable("firstName");
        String email = (String) delegateExecution.getVariable("email");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("UPP Membership information");
        mailMessage.setText(
                firstName +
                        ",\n committee has requested more of your works. You have 2 days to submit more works.");

        javaMailSender.send(mailMessage);
    }
}
