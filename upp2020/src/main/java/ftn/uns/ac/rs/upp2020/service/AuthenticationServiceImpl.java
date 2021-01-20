package ftn.uns.ac.rs.upp2020.service;


import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.dto.LoginDTO;
import ftn.uns.ac.rs.upp2020.exceptions.GeneralException;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public boolean login(LoginDTO dto) {
        return userRepository.findByUsernameAndVerifiedAndApproved(dto.getUsername(), true, true) != null;
    }

    @Override
    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());

        try {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return userService.findByUsername(userDetails.getUsername());
        } catch (Exception e) {
            return null;
        }

    }

}
