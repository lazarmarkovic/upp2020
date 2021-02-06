package ftn.uns.ac.rs.upp2020.controller;


import ftn.uns.ac.rs.upp2020.dto.*;
import ftn.uns.ac.rs.upp2020.security.TokenUtils;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
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
@RequestMapping("/users")
public class UserController {


    private final IdentityService identityService;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    private final TaskService taskService;
    private final FormService formService;

    @Autowired
    public UserController(IdentityService identityService,
                          RuntimeService runtimeService,
                          RepositoryService repositoryService,
                          TaskService taskService,
                          FormService formService) {
        this.identityService = identityService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.taskService = taskService;
        this.formService = formService;
    }

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping(path = "/start-author-registration", produces = "application/json")
    public @ResponseBody Boolean runAuthorRegistration(){
        identityService.setAuthenticatedUserId("guest");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("author_registration");
        runtimeService.setVariable(pi.getProcessInstanceId(), "starter", "guest");
        return true;
    }

    @GetMapping(path = "/start-reader-registration", produces = "application/json")
    public @ResponseBody Boolean runReaderRegistration(){
        identityService.setAuthenticatedUserId("guest");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("reader_registration");
        runtimeService.setVariable(pi.getProcessInstanceId(), "starter", "guest");
        return true;
    }


    @GetMapping(path = "/start-work-publishing", produces = "application/json")
    public @ResponseBody Boolean runWorkPublishing(){
        runtimeService.startProcessInstanceByKey("book_publishing");
        return true;
    }


    @GetMapping(path="/confirm-email/{processId}/{confCode}", produces = "application/json")
    public ResponseEntity<?> EmailVerification(
            @PathVariable String processId,
            @PathVariable String confCode) {

        runtimeService.setVariable(
                processId,
                "confirmationEmailCode",
                confCode);

        MessageCorrelationResult messageCorrelationResult =
                runtimeService
                    .createMessageCorrelation("CatchConfirmationEmailMessage")
                    .processInstanceId(processId)
                    .correlateWithResult();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    
    
    /* Generic APIs */
    @PostMapping(path = "/submit/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> submit(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {

        System.out.println(">> SUBMIT TASK: ");
        System.out.println(data);
        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));

        // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // String processInstanceId = task.getProcessInstanceId();

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}


