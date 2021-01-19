package ftn.uns.ac.rs.upp2020.camunda_delegate;

import java.util.Optional;

import javax.transaction.Transactional;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.upp2020.domain.Book;
import ftn.uns.ac.rs.upp2020.repository.BookRepository;

@Service
public class SetBookStatus implements JavaDelegate {

    @Autowired
    BookRepository bookRepository;

    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
            Long id =  (Long) execution.getVariable("book_id");

            Optional<Book> b = bookRepository.findById(id);

            if(b.isEmpty()) {
                System.out.println("Invalid book id");
                return;
            }

            Book book = b.get();
            book.setStatus(1);
            bookRepository.save(book);

    }
    
}

