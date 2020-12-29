package ftn.uns.ac.rs.upp2020.service;

import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean isUniqueEmail(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public boolean isUniqueUsername(String username) {
        return userRepository.findByUsername(username) == null;
    }
}
