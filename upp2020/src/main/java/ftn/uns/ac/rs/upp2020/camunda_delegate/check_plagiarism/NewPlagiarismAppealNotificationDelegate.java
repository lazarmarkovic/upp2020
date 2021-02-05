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
public class NewPlagiarismAppealNotificationDelegate implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void execute(DelegateExecution execution) throws Exception {

        User headEditor = (User) execution.getVariable("headEditorUser");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(headEditor.getEmail());
        mailMessage.setSubject("New Plagiarism Appeal");
        mailMessage.setText(String.format("%s, new plagiarism appeal has been made. " +
                "Please assign at least 2 editors to review it.", headEditor.getId()));

        javaMailSender.send(mailMessage);

        System.out.println("Head Editor obavesten");
    }

}
