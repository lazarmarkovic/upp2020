package ftn.uns.ac.rs.upp2020.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "aa__book")
@Getter
@Setter
public class Book {

    public Book(String title2, String synopsis2, String genre2, User u) {
        this.title = title2;
        this.synopsis = synopsis2;
        this.genre = genre2;
        this.user = u;
	}

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 512, nullable = false)
    private String title;

    @Column(name = "synopsis", length = 512, nullable = false)
    private String synopsis;

    @Column(name = "genre", length = 512, nullable = false)
    private String genre;

    @ ManyToOne
    private User user;
}
