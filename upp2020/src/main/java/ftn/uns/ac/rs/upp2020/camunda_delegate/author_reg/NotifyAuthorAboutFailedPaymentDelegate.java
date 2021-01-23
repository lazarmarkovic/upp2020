package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotifyAuthorAboutFailedPaymentDelegate implements JavaDelegate {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String firstName = (String) delegateExecution.getVariable("firstName");
        String email = (String) delegateExecution.getVariable("email");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("UPP Membership payment");
        mailMessage.setText(
                firstName +
                        ",\nYou failed to pay membership fees in due time. Your account remains unapproved.");

        javaMailSender.send(mailMessage);
    }
}
