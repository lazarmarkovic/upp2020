package ftn.uns.ac.rs.upp2020.controller;


import ftn.uns.ac.rs.upp2020.dto.InputDataDTO;
import ftn.uns.ac.rs.upp2020.security.TokenUtils;
import ftn.uns.ac.rs.upp2020.service.GenreService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tasks")
public class RegistrationController {

    private final IdentityService identityService;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final TokenUtils tokenUtils;

    private final TaskService taskService;
    private final FormService formService;
    private final GenreService genreService;


    @Autowired
    public RegistrationController(IdentityService identityService,
                          RuntimeService runtimeService,
                          RepositoryService repositoryService,
                          TokenUtils tokenUtils,
                          TaskService taskService,
                          FormService formService,
                          GenreService genreService) {
        this.identityService = identityService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.tokenUtils = tokenUtils;
        this.taskService = taskService;
        this.formService = formService;
        this.genreService = genreService;
    }


    @PostMapping(path="/submit-member-vote/{taskId}", consumes = "application/json")
    public ResponseEntity<?> submitMemberVote(
            @RequestBody List<InputDataDTO> data,
            @PathVariable String taskId) {
        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        List<String> committeeVotes = (List<String>)runtimeService.getVariable(processInstanceId, "committeeVotes");
        List<String> committeeComments = (List<String>)runtimeService.getVariable(processInstanceId, "committeeComments");

        committeeVotes.add(map.get("vote").toString());
        committeeComments.add(map.get("comment").toString());

        runtimeService.setVariable(processInstanceId, "committeeVotes", committeeVotes);
        runtimeService.setVariable(processInstanceId, "committeeComments", committeeComments);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
