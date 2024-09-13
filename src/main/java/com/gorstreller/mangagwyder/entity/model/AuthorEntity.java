package com.gorstreller.mangagwyder.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Table(name = "authors")
public class AuthorEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "dob", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private Set<MangaEntity> mangas;
}

