package ftn.uns.ac.rs.upp2020.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "aa__users")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<UserGenre> userGenres;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<PreviousWork> previousWorks;

    @Column(name = "username", length = 512, nullable = false)
    private String username;

    @Column(name = "password", length = 512, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 256, nullable = false)
    private Role role;

    @Column(name = "email", length = 512, nullable = false)
    private String email;

    @Column(name = "first_name", length = 512, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 512, nullable = false)
    private String lastName;

    @Column(name = "city", length = 512, nullable = false)
    private String city;

    @Column(name = "country", length = 512, nullable = false)
    private String country;

    @Column(name = "activation_code", length = 256, nullable = false)
    private String verificationCode;

    @Column(name = "activated", nullable = false)
    private Boolean verified;

    @Column(name = "approved", nullable = false)
    private Boolean approved;

    public User(String username,
                String password,
                Role role,
                String email,
                String firstName,
                String lastName,
                String city,
                String country,
                String verificationCode,
                Boolean verified,
                Boolean approved) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.verificationCode = verificationCode;
        this.verified = verified;
        this.approved = approved;
    }
}
