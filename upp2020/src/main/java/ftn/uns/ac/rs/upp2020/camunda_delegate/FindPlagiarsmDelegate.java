package ftn.uns.ac.rs.upp2020.camunda_delegate;

import java.util.List;
import java.util.Optional;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.upp2020.domain.Book;
import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.repository.BookRepository;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;

@Service
public class FindPlagiarsmDelegate implements JavaDelegate {


    @Autowired 
    BookRepository bookRepository;

    @Autowired 
    UserRepository userRepository;


    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String transcript = (String)  execution.getVariable("transcript");
        String writer = (String) execution.getVariable("loggedInWriter");
        Long book_id = (Long) execution.getVariable("book_id");

        User u = userRepository.findByUsername("pera");

        Optional<List<Book>> books = bookRepository.findAllBooksWithSameTranscript(transcript, u.getId());
        Book book = bookRepository.findById(book_id).get();

        if(books.isPresent() && books.get().size() > 0) {
            runtimeService.setVariable(execution.getProcessInstanceId(), "foundPlagiarism", true);
            return;
        }


        book.setTranscript(transcript);

        bookRepository.save(book);

        runtimeService.setVariable(execution.getProcessInstanceId(), "foundPlagiarism", false);
   

    }
    
}
