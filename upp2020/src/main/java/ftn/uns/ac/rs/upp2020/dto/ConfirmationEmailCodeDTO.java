package ftn.uns.ac.rs.upp2020.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.SequenceGenerators;

@Getter
@Setter
public class ConfirmationEmailCodeDTO {
    private String code;
}
