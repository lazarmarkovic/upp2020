package ftn.uns.ac.rs.upp2020.camunda_delegate;

import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmailDelegate implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("SEND VERIFICATION EMAIL");
        String registration = (String) execution.getVariable("username");

        String hashCode = RandomStringUtils.random(5, true, true);
        User u = userRepository.findByUsername(registration);
        u.setVerificationCode(hashCode);
        userRepository.save(u);

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(u.getEmail());
        mailMessage.setSubject("UPP account verification code");
        mailMessage.setText(registration +  ",\n Please verify your account.\n Your activation code is: " + hashCode);

        javaMailSender.send(mailMessage);
    }
}
