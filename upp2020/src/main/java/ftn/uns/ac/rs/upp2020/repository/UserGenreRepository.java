package ftn.uns.ac.rs.upp2020.repository;

import ftn.uns.ac.rs.upp2020.domain.Genre;
import ftn.uns.ac.rs.upp2020.domain.UserGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGenreRepository  extends JpaRepository<UserGenre, Long> {
    Optional<UserGenre> findById(Long id);
}