package ftn.uns.ac.rs.upp2020.dto;


import ftn.uns.ac.rs.upp2020.domain.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenDTO {
    private String token;
    private Role role;
}
