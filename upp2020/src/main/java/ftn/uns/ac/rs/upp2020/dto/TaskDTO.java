package ftn.uns.ac.rs.upp2020.dto;

import lombok.*;

import java.util.Date;


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
    Date creationTime;
}