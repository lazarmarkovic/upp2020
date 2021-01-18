package ftn.uns.ac.rs.upp2020.repository;

import ftn.uns.ac.rs.upp2020.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByVerificationCode(String a);

    Optional<User> findByEmail(String email);

    User findByUsername(String username);

    User findByUsernameAndVerified(String username, Boolean verified);

    @Query(value = "select t from User t where email = :email")
    User findByEmail(@Param("email") String email);

    @Query(value = "select t from User t where role = ftn.uns.ac.rs.upp2020.domain.Role.EDITOR")
    List<User> findAllActiveUsers();
}
