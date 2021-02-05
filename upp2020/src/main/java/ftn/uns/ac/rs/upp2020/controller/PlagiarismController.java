package ftn.uns.ac.rs.upp2020.controller;

import ftn.uns.ac.rs.upp2020.dto.InputDataDTO;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class PlagiarismController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @GetMapping(path = "/check-plagiarism", produces = "application/json")
    public @ResponseBody Boolean checkPlagiarism() {
        identityService.setAuthenticatedUserId("writer");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Check_Plagiarism");

        User writer = identityService.createUserQuery().userId("writer").singleResult();

        runtimeService.setVariable(pi.getProcessInstanceId(), "writer", writer);

        System.out.println("Checking plagiarism task started");

        /*Group headEditorGroup = identityService.newGroup("headEditor");
        identityService.saveGroup(headEditorGroup);

        User camundaUser = identityService.newUser("headEditorMisk");
        camundaUser.setEmail("mihajlo.jovkovic@gmail.com");
        camundaUser.setFirstName("Mihajlo");
        camundaUser.setLastName("Jovkovic");
        camundaUser.setPassword("miskmisk");
        identityService.saveUser(camundaUser);

        identityService.createMembership("headEditorMisk", "headEditor");*/


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

    @PostMapping(path = "/review_plagiarism_appeal/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> reviewAppeal(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {

        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceID = task.getProcessInstanceId();

        ArrayList<String> editorsNotes = (ArrayList<String>) runtimeService.getVariable(processInstanceID, "editorsNotes");
        editorsNotes.add(map.get("editorNote").toString());
        runtimeService.setVariable(processInstanceID, "editorsNotes", editorsNotes);

        ArrayList<User> chosenEditors = (ArrayList<User>) runtimeService.getVariable(processInstanceID,"chosenEditorsUsers");
        chosenEditors.removeIf(editor -> editor.getId().equals(task.getAssignee()));
        runtimeService.setVariable(processInstanceID, "chosenEditorsUsers", chosenEditors);

        ArrayList<User> availableEditors = (ArrayList<User>) runtimeService.getVariable(processInstanceID, "availableEditors");
        availableEditors.removeIf(editor -> editor.getId().equals(task.getAssignee()));
        runtimeService.setVariable(processInstanceID, "availableEditors", availableEditors);

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        System.out.println("Review added");
        return new ResponseEntity<> (HttpStatus.OK);
    }

    @PostMapping(path = "/cast_vote/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> castVote(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {

        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceID = task.getProcessInstanceId();

        ArrayList<Boolean> committeeVotes = (ArrayList<Boolean>) runtimeService.getVariable(processInstanceID, "committeeVotes");
        committeeVotes.add(Boolean.valueOf(map.get("vote").toString()));
        runtimeService.setVariable(processInstanceID, "committeeVotes", committeeVotes);

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        System.out.println("Vote cast");
        return new ResponseEntity<> (HttpStatus.OK);
    }

}
