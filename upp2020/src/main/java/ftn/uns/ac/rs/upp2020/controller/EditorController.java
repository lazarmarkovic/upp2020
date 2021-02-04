package ftn.uns.ac.rs.upp2020.controller;

import ftn.uns.ac.rs.upp2020.dto.*;
import ftn.uns.ac.rs.upp2020.security.TokenUtils;
import ftn.uns.ac.rs.upp2020.service.FileManipulationService;
import ftn.uns.ac.rs.upp2020.service.FileManipulationServiceImpl;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/editor")
public class EditorController {


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
    FileManipulationServiceImpl fileManipulationService;

    @Autowired
    private TokenUtils tokenUtils;

    
    @GetMapping(path = "/download-transcript/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> downloadTranscript() {
        // Load file as Resource
        Resource resource = fileManipulationService.downloadTranscript();

        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

        
    @PostMapping(path = "/send-full-transcript/{taskId}", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<?> trackChanges(@RequestParam("file") MultipartFile file, @PathVariable String taskId) {
        HashMap<String, Object> map = new HashMap<String,Object>();
        this.fileManipulationService.saveTranscript(file);

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<> (HttpStatus.OK);
    }


    @PostMapping(path = "/check-content/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> checkContent(@RequestBody List<InputDataDTO> data, @PathVariable String taskId) {
        HashMap<String, Object> map = (HashMap<String, Object>) data.stream()
                .collect(Collectors.toMap(InputDataDTO::getName, InputDataDTO::getValue));

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();


        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<> (HttpStatus.OK);
    }


}