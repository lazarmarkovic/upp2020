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
public class NewAppealNotification implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void execute(DelegateExecution execution) throws Exception {


        User leadEditor = identityService.createUserQuery().memberOfGroup("leadEditor").list().get(0);

        execution.setVariable("leadEditorUser", leadEditor);
        execution.setVariable("leadEditor", leadEditor.getId());

        System.out.println("Lead Editor obavesten");
        System.out.println(leadEditor.getId());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(leadEditor.getEmail());
        mailMessage.setSubject("New Plagiarism Appeal");
        mailMessage.setText(String.format("%s, new plagiarism appeal has been made. " +
                "Please assign at least 2 editors to review it.", leadEditor.getId()));


        javaMailSender.send(mailMessage);
    }

}
