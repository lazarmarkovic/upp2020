package ftn.uns.ac.rs.upp2020.repository;

import ftn.uns.ac.rs.upp2020.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByCode(String code);

    Genre findByName(String name);

    List<Genre> findAll();
}
