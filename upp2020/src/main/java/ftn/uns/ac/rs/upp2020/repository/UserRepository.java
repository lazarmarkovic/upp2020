package ftn.uns.ac.rs.upp2020.repository;


import ftn.uns.ac.rs.upp2020.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
