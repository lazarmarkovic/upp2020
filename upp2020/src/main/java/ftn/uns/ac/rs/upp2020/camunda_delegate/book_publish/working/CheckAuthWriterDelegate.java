package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish.working;

import ftn.uns.ac.rs.upp2020.domain.Role;
import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CheckAuthWriterDelegate implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("--- Task: check authenticated writer");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User authUser =
                (org.springframework.security.core.userdetails.User)auth.getPrincipal();

        User user = userService.findByUsername(authUser.getUsername());

        if (user == null ||
                !user.getRole().equals(Role.AUTHOR) ||
                !user.getApproved() ||
                !user.getVerified() ) {
            delegateExecution.setVariable("authorOK", false);
        } else {
            delegateExecution.setVariable("authorOK", true);
            delegateExecution.setVariable("starter", user.getUsername());
        }
    }
}
