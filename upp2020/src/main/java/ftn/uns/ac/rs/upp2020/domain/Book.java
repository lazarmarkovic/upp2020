package ftn.uns.ac.rs.upp2020.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "aa__book")
@Getter
@Setter
public class Book {

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 512, nullable = false)
    private String title;

    @Column(name = "synopsis", length = 512, nullable = false)
    private String synopsis;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @Column(name = "approved", nullable = false)
    private Boolean approved;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "transcript", columnDefinition = "MEDIUMBLOB", nullable = true)
    private byte[] transcript;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Book(String title, String synopsis, Genre genre, User user, boolean approved) {
        this.title = title;
        this.synopsis = synopsis;
        this.genre = genre;
        this.user = user;
        this.approved = approved;
        this.transcript = null;
    }

    public Book() {}
}
