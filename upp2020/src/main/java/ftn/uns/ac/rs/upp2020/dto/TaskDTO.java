package ftn.uns.ac.rs.upp2020.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDTO {
    String taskId;
    String name;
    String assignee;
    String name_id;
}