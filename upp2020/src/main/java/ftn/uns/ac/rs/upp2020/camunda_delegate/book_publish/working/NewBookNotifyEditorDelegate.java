package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish.working;

import ftn.uns.ac.rs.upp2020.domain.Role;
import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.repository.BookRepository;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NewBookNotifyEditorDelegate implements JavaDelegate {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("--- Task: notify head editor about new book publication");

        String username = (String) delegateExecution.getVariable("starter");
        User user = userService.findByUsername(username);

        List<User> headEditor = userRepository.findAllByRole(Role.HEAD_EDITOR);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(headEditor.get(0).getEmail());
        mailMessage.setSubject("Review synopsis - New book has entered publishing process");
        mailMessage.setText(
                headEditor.get(0).getFirstName() +
                        ",\n" + user.getFirstName() + " " + user.getLastName() +
                        " has submitted new book. Synopsis review pending.");

        javaMailSender.send(mailMessage);

        delegateExecution.setVariable("headEditor", headEditor.get(0).getUsername());
    }
}
