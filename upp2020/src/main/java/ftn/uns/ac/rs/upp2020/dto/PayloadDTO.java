package ftn.uns.ac.rs.upp2020.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PayloadDTO {
    private MultipartFile file;
    private String transcript;

}
