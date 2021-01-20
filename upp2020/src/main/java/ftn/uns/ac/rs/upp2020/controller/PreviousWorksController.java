package ftn.uns.ac.rs.upp2020.controller;

import ftn.uns.ac.rs.upp2020.domain.PreviousWork;
import ftn.uns.ac.rs.upp2020.exceptions.GeneralException;
import ftn.uns.ac.rs.upp2020.repository.PreviousWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/works")
public class PreviousWorksController {

    @Autowired
    PreviousWorkRepository previousWorkRepository;

    @GetMapping(value="/{workName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity downloadPDF(
            @PathVariable String workName) throws GeneralException {

        Optional<PreviousWork> opt = previousWorkRepository.findByName(workName);

        if (opt.isEmpty()) {
            throw new GeneralException("Work with given name not found.");
        }

        byte[] bytes = opt.get().getFile();
        System.out.println(opt.get().getName());

        String fileName = opt.get().getName();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName +"\"").body(bytes);

    }
}
