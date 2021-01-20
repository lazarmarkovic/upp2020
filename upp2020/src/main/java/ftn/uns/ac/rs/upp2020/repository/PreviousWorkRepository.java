package ftn.uns.ac.rs.upp2020.repository;

import ftn.uns.ac.rs.upp2020.domain.PreviousWork;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PreviousWorkRepository extends JpaRepository<PreviousWork, Long> {

    Optional<PreviousWork> findByName(String name);
}
