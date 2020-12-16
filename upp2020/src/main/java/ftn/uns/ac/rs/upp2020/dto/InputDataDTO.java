package ftn.uns.ac.rs.upp2020.dto;

import lombok.*;

import java.io.Serializable;



@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InputDataDTO implements Serializable {
    private String name;
    private Object value;
    private Boolean isList;
}