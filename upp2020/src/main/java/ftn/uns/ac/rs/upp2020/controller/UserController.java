package ftn.uns.ac.rs.upp2020.controller;

import ftn.uns.ac.rs.upp2020.dto.*;
import ftn.uns.ac.rs.upp2020.security.TokenUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    private TokenUtils tokenUtils;


    @GetMapping(path = "/start-author-registration", produces = "application/json")
    public @ResponseBody Boolean runRegistration(){
        identityService.setAuthenticatedUserId("guest");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("author_registration");
        runtimeService.setVariable(pi.getProcessInstanceId(), "starter", "guest");

        System.out.println("START AUTHOR REGISTRATION PROCESS");
        return true;
    }


    @PostMapping(path = "/submit/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> submit(
            @RequestBody List<InputDataDTO> data,
            @PathVariable String taskId ){

        System.out.println(">> SUBMIT TASK: ");
        System.out.println(data);
        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));

        //Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //String processInstanceId = task.getProcessInstanceId();

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<> (HttpStatus.OK);
    }


    @GetMapping(path = "/tasks", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDTO>> get(HttpServletRequest http){
        String authToken = http.getHeader("X-Auth-Token");

        String username = "guest";
        if (authToken != null){
            username = this.tokenUtils.getUsernameFromToken(authToken);
        }

        List<Task> tasks= taskService.createTaskQuery().taskAssignee(username).list();
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(t -> {
                    String taskName = t.getName().split(" ")[0];
                    return new TaskDTO(t.getId(), t.getName(), t.getAssignee(), taskName);
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    }
}
