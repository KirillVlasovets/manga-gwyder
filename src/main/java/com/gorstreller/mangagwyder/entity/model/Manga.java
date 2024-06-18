package com.gorstreller.mangagwyder.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Entity
@Table(name = "manga")
public class Manga implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "manga_authors",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonIgnore
    private Set<Author> authors;
}
