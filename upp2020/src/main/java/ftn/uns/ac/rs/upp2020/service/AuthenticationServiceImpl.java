package ftn.uns.ac.rs.upp2020.service;


import ftn.uns.ac.rs.upp2020.dto.LoginDTO;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean login(LoginDTO dto) {
        return userRepository.findByUsernameAndVerified(dto.getUsername(), true) != null;
    }


}
