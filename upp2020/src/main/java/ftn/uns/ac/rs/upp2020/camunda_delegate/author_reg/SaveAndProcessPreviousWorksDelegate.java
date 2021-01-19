package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.domain.Work;
import ftn.uns.ac.rs.upp2020.repository.WorkRepository;
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
    WorkRepository workRepository;

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
            Work work = new Work();
            work.setName("Neki fajl");
            work.setFile(f);
            work.setUser(user);
            workRepository.save(work);
        }));
    }
}
