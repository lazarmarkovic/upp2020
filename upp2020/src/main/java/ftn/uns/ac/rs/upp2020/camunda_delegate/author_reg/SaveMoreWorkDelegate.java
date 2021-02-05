package ftn.uns.ac.rs.upp2020.camunda_delegate.author_reg;


import ftn.uns.ac.rs.upp2020.domain.PreviousWork;
import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.dto.FileHolderDTO;
import ftn.uns.ac.rs.upp2020.repository.PreviousWorkRepository;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveMoreWorkDelegate implements JavaDelegate {

    @Autowired
    PreviousWorkRepository previousWorkRepository;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("--- Task: save more work");

        String username = (String) delegateExecution.getVariable("username");
        User user = userService.findByUsername(username);
        List<FileHolderDTO> pdfFiles = (List<FileHolderDTO>) delegateExecution.getVariable("files");

        pdfFiles.forEach((f -> {
            System.out.println("**** Save work: " + f.getFileName());
            PreviousWork previousWork = new PreviousWork();
            previousWork.setName(f.getFileName());
            previousWork.setFile(f.getFile());
            previousWork.setUser(user);
            previousWorkRepository.save(previousWork);
        }));
    }
}
