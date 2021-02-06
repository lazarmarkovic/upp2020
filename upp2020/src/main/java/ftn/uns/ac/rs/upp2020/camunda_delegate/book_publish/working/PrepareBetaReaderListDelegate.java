package ftn.uns.ac.rs.upp2020.camunda_delegate.book_publish.working;

import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.domain.UserGenre;
import ftn.uns.ac.rs.upp2020.repository.UserGenreRepository;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrepareBetaReaderListDelegate implements JavaDelegate {

    @Autowired
    UserGenreRepository userGenreRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String genre = (String) delegateExecution.getVariable("genre");

        List<User> users = userGenreRepository.findAll()
                .stream()
                .filter((ug) -> ug.getGenre().getCode().equals(genre))
                .map(UserGenre::getUser)
                .collect(Collectors.toList());

        delegateExecution.setVariable("beta_readers", users);
    }
}
