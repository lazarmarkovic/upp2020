package ftn.uns.ac.rs.upp2020.camunda_listener;

import ftn.uns.ac.rs.upp2020.domain.Genre;
import ftn.uns.ac.rs.upp2020.repository.GenreRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class GetGenreFieldsListener implements ExecutionListener {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        ArrayList<Genre> genres = (ArrayList<Genre>) genreRepository.findAll();
        System.out.println("Get all genre fields - LISTENER");
        execution.setVariable("allGenres", genres);
    }
}
