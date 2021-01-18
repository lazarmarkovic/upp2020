package ftn.uns.ac.rs.upp2020.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserDTO {
    private Long id;
    private List<String> genres;
    private String role;

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private Boolean verified;
}
