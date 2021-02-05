package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import ftn.uns.ac.rs.upp2020.domain.Book;
import ftn.uns.ac.rs.upp2020.repository.BookRepository;
import ftn.uns.ac.rs.upp2020.service.BookService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MarkBookAsPlagiarismDelegate implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Book plagiarizedBook = bookService.findBookByTitle(execution.getVariable("plagiarizedTitle").toString());
        bookService.markPlagiarism(plagiarizedBook);
        System.out.println("Book marked as plagiarism");


    }
}
