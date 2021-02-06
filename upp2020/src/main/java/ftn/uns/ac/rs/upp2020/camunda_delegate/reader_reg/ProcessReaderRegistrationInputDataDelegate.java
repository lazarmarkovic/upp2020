package ftn.uns.ac.rs.upp2020.camunda_delegate.reader_reg;


import ftn.uns.ac.rs.upp2020.domain.Role;
import ftn.uns.ac.rs.upp2020.domain.UserGenre;
import ftn.uns.ac.rs.upp2020.exceptions.GeneralException;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessReaderRegistrationInputDataDelegate implements JavaDelegate {

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
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("--- Task: process reader registration input data");

        String password = (String) delegateExecution.getVariable("password");
        String username = (String) delegateExecution.getVariable("username");
        String email = (String) delegateExecution.getVariable("email");

        String firstName = (String) delegateExecution.getVariable("firstName");
        String lastName = (String) delegateExecution.getVariable("lastName");
        String city = (String) delegateExecution.getVariable("city");
        String country = (String) delegateExecution.getVariable("country");


        Boolean isBeta = (Boolean) delegateExecution.getVariable("isBeta");

        User findUser = identityService.createUserQuery().userId(username).singleResult();

        if (findUser != null) {
            delegateExecution.setVariable("isRegFormValid", false);
            throw new GeneralException("User already exists!");
        }


        List<String> genres = new ArrayList<>();
        Role userRole = Role.READER;
        if (isBeta) {
            genres = (List<String>) delegateExecution.getVariable("selectGenres");
            userRole = Role.BETA_READER;
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
                        userRole,
                        email,
                        firstName,
                        lastName,
                        city,
                        country,
                        "",
                        false,
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

        delegateExecution.setVariable("isRegFormValid", true);
    }
}
