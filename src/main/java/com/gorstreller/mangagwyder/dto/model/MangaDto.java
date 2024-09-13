package com.gorstreller.mangagwyder.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class MangaDto implements Serializable {

    private Long id;
    private String title;
    private String description;
}
