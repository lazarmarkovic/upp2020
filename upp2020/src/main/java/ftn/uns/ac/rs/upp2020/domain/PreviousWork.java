package ftn.uns.ac.rs.upp2020.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "aa__previous_works")
@Getter
@Setter
public class PreviousWork {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "name", length = 512, nullable = false)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "file", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] file;
}
