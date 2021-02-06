package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish.working;

import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotifyAuthorToSendTranscriptDelegate implements JavaDelegate {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("--- Task: notify author to send book transcript");

        String username = (String) delegateExecution.getVariable("starter");
        User user = userService.findByUsername(username);

        String bookTitle = (String) delegateExecution.getVariable("title");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Please upload transcript - Book publishing status");
        mailMessage.setText(
                user.getFirstName() +
                        ",\nEditor has requested to upload transcript of book titled'" +
                        bookTitle + ".");

        javaMailSender.send(mailMessage);
    }
}
