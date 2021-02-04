package ftn.uns.ac.rs.upp2020.camunda_delegate.check_plagiarism;

import ftn.uns.ac.rs.upp2020.domain.Book;
import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.service.BookService;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ValidatePlagiarismAppeal implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Book myBook = bookService.findBookByTitle(execution.getVariable("myTitle").toString());
        Book plagiarizedBook = bookService.findBookByTitle(execution.getVariable("plagiarizedTitle").toString());
        User author = userService.findByUsername(execution.getVariable("author").toString());

        if (!author.equals(plagiarizedBook.getUser()))
            throw new Exception(String.format("%s did not write %s",author.getUsername(), plagiarizedBook.getTitle() ));



        System.out.println("In validation delegate");
        System.out.println(myBook.getTitle());
        System.out.println(plagiarizedBook.getTitle());
        System.out.println(author.getUsername());

    }
}
