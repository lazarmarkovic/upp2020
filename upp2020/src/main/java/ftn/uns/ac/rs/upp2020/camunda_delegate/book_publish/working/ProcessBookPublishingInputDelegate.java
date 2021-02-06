package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish.working;

import ftn.uns.ac.rs.upp2020.domain.Book;
import ftn.uns.ac.rs.upp2020.domain.Genre;
import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.repository.BookRepository;
import ftn.uns.ac.rs.upp2020.repository.GenreRepository;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessBookPublishingInputDelegate implements JavaDelegate {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserService userService;

    @Autowired
    GenreRepository genreRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("--- Task: process book publishing input");

        String title = (String) delegateExecution.getVariable("title");
        String genre = (String) delegateExecution.getVariable("selectGenre");
        String synopsis = (String) delegateExecution.getVariable("synopsis");

        String username = (String) delegateExecution.getVariable("starter");
        User user = userService.findByUsername(username);

        Genre genreObject = genreRepository.findByCode(genre);
        Book book = new Book(title, synopsis, genreObject, user, false);

        Book savedBook = bookRepository.save(book);

        delegateExecution.setVariable("bookId", savedBook.getId());
        delegateExecution.setVariable("title", title);
        delegateExecution.setVariable("genre", genre);
        delegateExecution.setVariable("synopsis", synopsis);
    }
}
