package ftn.uns.ac.rs.upp2020.controller;

import ftn.uns.ac.rs.upp2020.dto.InputDataDTO;
import ftn.uns.ac.rs.upp2020.repository.UserRepository;
import ftn.uns.ac.rs.upp2020.security.TokenUtils;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class WriterController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping(path = "/check-plagiarism", produces = "application/json")
    public @ResponseBody Boolean checkPlagiarism() {
        identityService.setAuthenticatedUserId("writer");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Check_Plagiarism");
        runtimeService.setVariable(pi.getProcessInstanceId(), "writer", "writer");

        System.out.println("Checking plagiarism task started");

        /*Group leadEditorGroup = identityService.newGroup("leadEditor");
        identityService.saveGroup(leadEditorGroup);

        User camundaUser = identityService.newUser("leadEditorMisk");
        camundaUser.setEmail("mihajlo.jovkovic@gmail.com");
        camundaUser.setFirstName("Mihajlo");
        camundaUser.setLastName("Jovkovic");
        camundaUser.setPassword("miskmisk");
        identityService.saveUser(camundaUser);

        identityService.createMembership("leadEditorMisk", "leadEditor");
        */

        return true;
    }
    @PostMapping(path = "/appeal/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> appeal(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {

        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));
        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        System.out.println("Appeal made");
        return new ResponseEntity<> (HttpStatus.OK);
    }

    @PostMapping(path = "/choose_editors/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> chooseEditors(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {

        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));
        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        System.out.println("Editors chosen");
        return new ResponseEntity<> (HttpStatus.OK);
    }

}
