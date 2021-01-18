package ftn.uns.ac.rs.upp2020.controller;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



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


    @GetMapping(path = "/start-author-registration", produces = "application/json")
    public @ResponseBody Boolean runRegistration(){
        identityService.setAuthenticatedUserId("guest");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("author_registration");
        runtimeService.setVariable(pi.getProcessInstanceId(), "starter", "guest");

        System.out.println("START AUTHOR REGISTRATION PROCESS");
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
}
