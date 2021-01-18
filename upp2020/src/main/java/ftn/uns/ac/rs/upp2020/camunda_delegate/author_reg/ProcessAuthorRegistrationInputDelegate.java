package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;

import ftn.uns.ac.rs.upp2020.domain.UserGenre;
import ftn.uns.ac.rs.upp2020.exceptions.GeneralException;
import ftn.uns.ac.rs.upp2020.domain.Role;
import ftn.uns.ac.rs.upp2020.repository.GenreRepository;
import ftn.uns.ac.rs.upp2020.repository.UserGenreRepository;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class ProcessAuthorRegistrationInputDelegate implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private UserGenreRepository userGenreRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("PROCESS REGISTRATION INPUT DATA");
        String password = (String) execution.getVariable("password");
        String username = (String) execution.getVariable("username");
        String email = (String) execution.getVariable("email");

        String firstName = (String) execution.getVariable("firstName");
        String lastName = (String) execution.getVariable("lastName");
        String city = (String) execution.getVariable("city");
        String country = (String) execution.getVariable("country");
        List<String> genres = (List<String>) execution.getVariable("select_genres");


        User findUser = identityService.createUserQuery().userId(username).singleResult();

        if (findUser != null) {
            execution.setVariable("isRegFormValid", false);
            throw new GeneralException("User already exists!");
        }

        if (genres.size() < 1) {
            execution.setVariable("isRegFormValid", false);
            throw new GeneralException("Author must select at least one genre.");
        }


        User newUser = identityService.newUser("");
        newUser.setId(username);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);

        identityService.saveUser(newUser);


        ftn.uns.ac.rs.upp2020.domain.User user =
               new ftn.uns.ac.rs.upp2020.domain.User(
                       username,
                       passwordEncoder.encode(password),
                       Role.AUTHOR,
                       email,
                       firstName,
                       lastName,
                       city,
                       country,
                       "",
                       false);

        // Save new user
        ftn.uns.ac.rs.upp2020.domain.User savedUser = userRepository.save(user);


        // Save userGenres
        genres.forEach((g) -> {
            UserGenre userGenre = new UserGenre();
            userGenre.setUser(savedUser);
            userGenre.setGenre(genreRepository.findByCode(g));
            userGenreRepository.save(userGenre);
        });

        execution.setVariable("isRegFormValid", true);
    }
}
