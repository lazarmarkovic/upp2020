package ftn.uns.ac.rs.upp2020.service;



import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.dto.LoginDTO;

public interface AuthenticationService {
    boolean login(LoginDTO dto);

    User getAuthUser();
}
