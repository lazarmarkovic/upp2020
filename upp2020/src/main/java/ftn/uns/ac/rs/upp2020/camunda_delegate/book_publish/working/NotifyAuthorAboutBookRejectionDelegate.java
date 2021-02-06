package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish.working;

import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.repository.BookRepository;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class NotifyAuthorAboutBookRejectionDelegate implements JavaDelegate {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserService userService;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("starter");
        User user = userService.findByUsername(username);

        String bookTitle = (String) delegateExecution.getVariable("title");
        String explanation = (String) delegateExecution.getVariable("explanation");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Rejected - Book publishing status");
        mailMessage.setText(
                user.getFirstName() +
                        ",\nWe are sad to inform you that your book '" +
                        bookTitle +
                        "' has been rejected by our head editor." +
                         "\nEditor explanation:" +
                        "\n" + explanation);

        javaMailSender.send(mailMessage);
    }
}
