package ftn.uns.ac.rs.upp2020.controller;

import ftn.uns.ac.rs.upp2020.domain.Genre;
import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.dto.FileHolderDTO;
import ftn.uns.ac.rs.upp2020.dto.FormDTO;
import ftn.uns.ac.rs.upp2020.dto.InputDataDTO;
import ftn.uns.ac.rs.upp2020.dto.TaskDTO;
import ftn.uns.ac.rs.upp2020.security.TokenUtils;
import ftn.uns.ac.rs.upp2020.service.AuthenticationService;
import ftn.uns.ac.rs.upp2020.service.GenreService;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final IdentityService identityService;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final TokenUtils tokenUtils;

    private final AuthenticationService authenticationService;
    private final TaskService taskService;
    private final FormService formService;
    private final GenreService genreService;
    private final UserService userService;


    @Autowired
    public TaskController(IdentityService identityService,
                          RuntimeService runtimeService,
                          RepositoryService repositoryService,
                          TokenUtils tokenUtils,
                          AuthenticationService authenticationService,
                          TaskService taskService,
                          FormService formService,
                          GenreService genreService,
                          UserService userService) {
        this.identityService = identityService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.tokenUtils = tokenUtils;
        this.authenticationService = authenticationService;
        this.taskService = taskService;
        this.formService = formService;
        this.genreService = genreService;
        this.userService = userService;
    }

    /* Generic APIs */
    @PostMapping(path = "/submit/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> submit(
            @RequestBody List<InputDataDTO> data,
            @PathVariable String taskId ){

        System.out.println(">> SUBMIT TASK: ");
        System.out.println(data);
        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<> (HttpStatus.OK);
    }


    @GetMapping(path = "", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDTO>> get(HttpServletRequest http){
        String username = "guest";
        User user = this.authenticationService.getAuthUser();

        if (user != null) {
            username = user.getUsername();
        }

        System.out.println("Current user: " + username);

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


    @GetMapping(path="/{taskId}/form", produces = "application/json")
    public @ResponseBody FormDTO getFrom(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TaskFormData tfd = formService.getTaskFormData(task.getId());

        List<InputDataDTO> readonlyFields = new ArrayList<>();
        List<FormField> fields = tfd.getFormFields();

        List<FormField> fieldReadonly = new ArrayList<>();
        for(FormField fp : fields){
            for (FormFieldValidationConstraint fc : fp.getValidationConstraints()){
                if(fc.getName().equals("readonly")){
                    fieldReadonly.add(fp);
                }
            }
        }
        List<FormField> properties = new ArrayList<>();

        //prodjemo kroz sva polja i proverimo da li postoje u readonly, ako ne postoje dodamo ih u properties
        for(FormField fp : fields){
            if (!fieldReadonly.contains(fp)){
                properties.add(fp); //ovo su sad sva polja koja nisu readonly
            }
        }

        for (FormField fp : fieldReadonly) {
            System.out.println("Field " + fp.getLabel() + " is readonly");
            System.out.println(fp.getTypeName());

            if (fp.getTypeName().equals("multiselectGenre")) {
                System.out.println("Field type is: " + fp.getTypeName());
                List<String> values = genreService.getAll().stream().map((Genre::getName)).collect(Collectors.toList());
                readonlyFields.add(new InputDataDTO(fp.getLabel(), values, true));

            } else  if (fp.getTypeName().equals("multiselectPDF")) {
                System.out.println("Field type is: " + fp.getTypeName());
                String processInstanceId = task.getProcessInstanceId();
                String username = (String) runtimeService.getVariable(processInstanceId, "username");
                User user = userService.findByUsername(username);
                List<String> values = user.getPreviousWorks().stream().map((w) -> "http://localhost:8080/works/" + w.getName()).collect(Collectors.toList());
                readonlyFields.add(new InputDataDTO(fp.getLabel(), values, true));
            } else {
                System.out.println("Nije usao. Field type is: " + fp.getTypeName());
                readonlyFields.add(new InputDataDTO(fp.getLabel(), fp.getValue().getValue(), false));
            }
        }

        return new FormDTO(task.getName() ,task.getId(), task.getProcessInstanceId(), properties, readonlyFields);
    }


    @PostMapping(value = "/{taskId}/upload")
    public ResponseEntity fileUpload(@RequestParam("files") MultipartFile[] files, @PathVariable String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstance = task.getProcessInstanceId();

        List<MultipartFile> fileList = new ArrayList<>(Arrays.asList(files));

        System.out.println("---Uploaded files names: ");
        List<FileHolderDTO> bytesList = fileList.stream().map((f) -> {
            try {
                System.out.println(f.getOriginalFilename());
                return new FileHolderDTO(f.getOriginalFilename(), f.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());

        runtimeService.removeVariable(processInstance, "files");
        runtimeService.setVariable(processInstance, "files", bytesList);

        return new ResponseEntity(HttpStatus.OK);
    }


}
