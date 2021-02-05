package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class ApprovedPlagiarismAppealApplicantNotificationDelegate implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        User applicant = (User) execution.getVariable("writer");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(applicant.getEmail());
        mailMessage.setSubject("Plagiarism Appeal Accepted");
        mailMessage.setText(String.format("%s, your plagiarism appeal has been accepted.", applicant.getId()));

        javaMailSender.send(mailMessage);

        System.out.println("Applicant obavesten");
    }

}
