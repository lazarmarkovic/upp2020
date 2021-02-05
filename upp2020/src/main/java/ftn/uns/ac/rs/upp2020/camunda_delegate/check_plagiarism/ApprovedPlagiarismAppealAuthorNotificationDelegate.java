package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ApprovedPlagiarismAppealAuthorNotificationDelegate implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        User author = identityService.createUserQuery().userId(execution.getVariable("author").toString()).singleResult();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(author.getEmail());
        mailMessage.setSubject("Plagiarism Appeal Accepted");
        mailMessage.setText(String.format("%s, your book has been marked as plagiarism.", author.getId()));

        javaMailSender.send(mailMessage);

        System.out.println("Author obavesten");
    }
}
