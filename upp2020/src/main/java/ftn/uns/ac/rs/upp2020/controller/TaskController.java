package ftn.uns.ac.rs.upp2020.controller;

import ftn.uns.ac.rs.upp2020.domain.Genre;
import ftn.uns.ac.rs.upp2020.dto.FormDTO;
import ftn.uns.ac.rs.upp2020.dto.InputDataDTO;
import ftn.uns.ac.rs.upp2020.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final IdentityService identityService;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    private final TaskService taskService;
    private final FormService formService;
    private final GenreService genreService;


    @Autowired
    public TaskController(IdentityService identityService,
                          RuntimeService runtimeService,
                          RepositoryService repositoryService,
                          TaskService taskService,
                          FormService formService,
                          GenreService genreService) {
        this.identityService = identityService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.taskService = taskService;
        this.formService = formService;
        this.genreService = genreService;
    }


    @GetMapping(path="/task/{taskId}/form", produces = "application/json")
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
            System.out.println("Polje " + fp.getLabel() + " je readonly");
            System.out.println(fp.getTypeName());

            if (fp.getTypeName().equals("multiselectGenre")) {
                System.out.println("Usao. Field type is: " + fp.getTypeName());
                List<String> values = genreService.getAll().stream().map((Genre::getName)).collect(Collectors.toList());
                readonlyFields.add(new InputDataDTO(fp.getLabel(), values, true));
            } else {
                System.out.println("Nije usao. Field type is: " + fp.getTypeName());
                readonlyFields.add(new InputDataDTO(fp.getLabel(), fp.getValue().getValue(), false));
            }
        }

        return new FormDTO(task.getId(), task.getProcessInstanceId(), properties, readonlyFields);
    }

}
