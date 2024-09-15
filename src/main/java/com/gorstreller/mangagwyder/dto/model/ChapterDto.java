package com.gorstreller.mangagwyder.dto.model;

import com.gorstreller.mangagwyder.entity.model.MangaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChapterDto {

    private Integer number;
    private String title;
    private MangaEntity manga;
}
