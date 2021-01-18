package ftn.uns.ac.rs.upp2020.camunda_delegate;

import org.camunda.bpm.engine.IdentityService;
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
public class BookPublisherDelegate implements JavaDelegate {

    
    @Autowired
    IdentityService identityService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            String title = (String) execution.getVariable("title");
            String synopsis = (String) execution.getVariable("synopsis");
            String genre = (String) execution.getVariable("genre");
            Boolean approve = (Boolean) execution.getVariable("approve");
    
            System.out.println("here");
    
            String writer = (String) execution.getVariable("loggedInWriter");
    
            System.out.println(writer);
    
            User u = userRepository.findByUsername("pera");
    
            System.out.println(u.toString());
    
            Book b = new Book(title, synopsis, genre, u, 0);
    
            bookRepository.save(b);

            System.out.println(execution.getProcessInstanceId());

            runtimeService.setVariable(execution.getProcessInstanceId(), "title", title);
            runtimeService.setVariable(execution.getProcessInstanceId(), "synopsis", synopsis);
            runtimeService.setVariable(execution.getProcessInstanceId(), "genre", genre);

            runtimeService.setVariable(execution.getProcessInstanceId(), "editor", "editor");

            runtimeService.setVariable(execution.getProcessInstanceId(), "approve", approve);
    
            System.out.println(writer);
        } catch (Exception e ) {
            e.printStackTrace();
        }

    }
    
}
