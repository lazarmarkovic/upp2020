package ftn.uns.ac.rs.upp2020.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "aa__genre")
@Getter
@Setter
public class Genre implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 512, nullable = false)
    private String code;

    @Column(name = "name", length = 512, nullable = false)
    private String name;
}
