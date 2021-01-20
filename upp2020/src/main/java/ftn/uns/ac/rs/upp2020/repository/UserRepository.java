package ftn.uns.ac.rs.upp2020.repository;


import ftn.uns.ac.rs.upp2020.domain.Role;
import ftn.uns.ac.rs.upp2020.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByVerificationCode(String a);

    Optional<User> findByEmail(String email);

    User findByUsername(String username);

    User findByUsernameAndVerifiedAndApproved(String username, Boolean verified, Boolean approved);

    List<User> findAllByRole(Role role);
}
