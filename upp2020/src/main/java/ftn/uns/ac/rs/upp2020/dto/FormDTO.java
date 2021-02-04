package ftn.uns.ac.rs.upp2020.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.camunda.bpm.engine.form.FormField;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormDTO {
    private String taskId;
    private String processInstanceId;
    private List<FormField> formFields;
    private List<InputDataDTO> readonlyFields;
}
