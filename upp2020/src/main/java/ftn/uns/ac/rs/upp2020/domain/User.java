package ftn.uns.ac.rs.upp2020.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Where(clause="deleted_at IS NULL")
@Table(name = "aa__users")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 512, nullable = false)
    private String username;

    @Column(name = "password", length = 512, nullable = false)
    private String password;

    @Column(name = "email", length = 512, nullable = false)
    private String email;

    @Column(name = "role", length = 256, nullable = false)
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public User() {
        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;
    }
}
