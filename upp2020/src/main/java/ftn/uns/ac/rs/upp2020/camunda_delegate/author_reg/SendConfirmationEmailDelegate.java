package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;

import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

@Service
public class SendConfirmationEmailDelegate implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("SEND VERIFICATION EMAIL");
        String username = (String) execution.getVariable("username");

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hash));

        User u = userRepository.findByUsername(username);
        u.setVerificationCode(sha256hex);
        userRepository.save(u);

        String processId = execution.getProcessInstanceId();
        String emailConfLink =
                String.format("http://localhost:8080/user/confirm-email/%s/%s", processId, sha256hex);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(u.getEmail());
        mailMessage.setSubject("UPP account email confirmation code");
        mailMessage.setText(
                username +
                ",\n Please confirm your email.\n Your confirmation link: " +
                emailConfLink);

        javaMailSender.send(mailMessage);
    }
}
