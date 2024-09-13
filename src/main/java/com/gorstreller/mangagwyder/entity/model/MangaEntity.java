package com.gorstreller.mangagwyder.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Entity
@Table(name = "manga")
public class MangaEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(name = "title")
    @Setter
    private String title;

    @Column(name = "description")
    @Setter
    private String description;

    @OneToMany(mappedBy= "manga")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<ChapterEntity> chapters;

    @ManyToMany
    @JoinTable(
            name = "manga_authors",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonIgnore
    private Set<AuthorEntity> authors;
}
