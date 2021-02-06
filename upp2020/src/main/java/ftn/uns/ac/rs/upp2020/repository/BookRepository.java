package ftn.uns.ac.rs.upp2020.repository;

import ftn.uns.ac.rs.upp2020.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long> {

    @Query(value = "select b from Book b where b.id = :bookId")
    Optional<Book> findById(Long bookId);

    @Query(value = "select b from Book b where b.transcript = :transcript AND b.id <> :bookId")
    Optional<List<Book>> findAllBooksWithSameTranscript(String transcript, Long bookId);
}