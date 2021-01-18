package ftn.uns.ac.rs.upp2020.service;

import ftn.uns.ac.rs.upp2020.domain.User;

public interface UserService {
    User findByUsername(String username);

    boolean isUniqueEmail(String email);

    boolean isUniqueUsername(String username);
}
