package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.domain.PreviousWork;
import ftn.uns.ac.rs.upp2020.repository.PreviousWorkRepository;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SaveAndProcessPreviousWorksDelegate  implements JavaDelegate {

    @Autowired
    PreviousWorkRepository previousWorkRepository;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("SAVE AND PROCESS PREVIOUS WORKS");

        String username = (String) execution.getVariable("username");
        User user = userService.findByUsername(username);
        List<byte[]> pdfFiles = (List<byte[]>) execution.getVariable("files");

        pdfFiles.forEach((f -> {
            PreviousWork previousWork = new PreviousWork();
            previousWork.setName("Neki fajl");
            previousWork.setFile(f);
            previousWork.setUser(user);
            previousWorkRepository.save(previousWork);
        }));
    }
}
