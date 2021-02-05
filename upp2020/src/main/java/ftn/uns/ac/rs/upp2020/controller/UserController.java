package ftn.uns.ac.rs.upp2020.controller;


import ftn.uns.ac.rs.upp2020.dto.*;
import ftn.uns.ac.rs.upp2020.security.TokenUtils;
import ftn.uns.ac.rs.upp2020.service.FileManipulationService;
import ftn.uns.ac.rs.upp2020.service.FileManipulationServiceImpl;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/users")
public class UserController {


    private final IdentityService identityService;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    private final TaskService taskService;
    private final FormService formService;


    @Autowired
    FileManipulationServiceImpl fileServiceManipulation;

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

    @GetMapping(path = "/tasks", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDTO>> get(HttpServletRequest http) {
        String authToken = http.getHeader("X-Auth-Token");

        String username = "guest";
        if (authToken != null) {
            username = this.tokenUtils.getUsernameFromToken(authToken);
        }

        List<Task> tasks= taskService.createTaskQuery().taskAssignee(username).list();
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(t -> {
                    String taskName = t.getName().split(" ")[0];
                    return new TaskDTO(t.getId(), t.getName(), t.getAssignee(), taskName, t.getCreateTime());
                })
                .sorted(Comparator.comparing(TaskDTO::getCreationTime))
                .collect(Collectors.toList());

        return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    }


    @GetMapping(path = "/editor-tasks", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDTO>> getEditorTasks(HttpServletRequest http) {
        String authToken = http.getHeader("X-Auth-Token");

        String username = "editor";
        if (authToken != null) {
            username = this.tokenUtils.getUsernameFromToken(authToken);
        }

        List<Task> tasks= taskService.createTaskQuery().taskAssignee(username).list();
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(t -> {
                    String taskName = t.getName().split(" ")[0];
                    return new TaskDTO(t.getId(), t.getName(), t.getAssignee(), taskName, t.getCreateTime());
                })
                .sorted(Comparator.comparing(TaskDTO::getCreationTime))
                .collect(Collectors.toList());

        return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    }


    @GetMapping(path = "/writer-tasks", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDTO>> getWriterTasks(HttpServletRequest http) {
        String authToken = http.getHeader("X-Auth-Token");

        String username = "writer";
        if (authToken != null) {
            username = this.tokenUtils.getUsernameFromToken(authToken);
        }

        List<Task> tasks= taskService.createTaskQuery().taskAssignee(username).list();
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(t -> {
                    String taskName = t.getName().split(" ")[0];
                    return new TaskDTO(t.getId(), t.getName(), t.getAssignee(), taskName, t.getCreateTime());
                })
                .sorted(Comparator.comparing(TaskDTO::getCreationTime))
                .collect(Collectors.toList());

        return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    }

    @GetMapping(path = "/publish-book", produces = "application/json")
    public @ResponseBody Boolean publishBook() {
        identityService.setAuthenticatedUserId("writer");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_0qg34ld");
        runtimeService.setVariable(pi.getProcessInstanceId(), "writer", "writer");
        runtimeService.setVariable(pi.getProcessInstanceId(), "editor", "editor");


        System.out.println("PUBLISH STARTED");
        return true;
    }

    @PostMapping(path = "/publish/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> publish(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {
        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));
        
        System.out.println(taskId);
        System.out.println(runtimeService.toString());

        // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // String processInstanceId = task.getProcessInstanceId();


        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<> (HttpStatus.OK);
    }

    @PostMapping(path = "/review/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> approveReview(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {
        try {
            HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                    .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));

            // Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            // String processInstanceId = task.getProcessInstanceId();

            try {
                formService.submitTaskForm(taskId, map);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PostMapping(path = "/explanation/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> createExplanation(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {
        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));
        
        System.out.println(taskId);
        System.out.println(runtimeService.toString());

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "editor", "editor");


        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<> (HttpStatus.OK);
    }


    @PostMapping(path = "/send-full-transcript/{taskId}", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<?> uploadTranscript(@RequestParam("file") MultipartFile file, @PathVariable String taskId) {
        HashMap<String, Object> map = new HashMap<String,Object>();

        this.fileServiceManipulation.saveTranscript(file);
        try {   
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<> (HttpStatus.OK);
    }


    
    @PostMapping(path = "/read-transcript/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> reviewTranscript(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {
        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "editor", "editor");

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<> (HttpStatus.OK);
    }

}


