package com.gorstreller.mangagwyder.dto.wrapper;

import com.gorstreller.mangagwyder.dto.model.ChapterDto;
import com.gorstreller.mangagwyder.entity.model.ChapterEntity;

public class ChapterWrapper {

    public static ChapterDto toDto(ChapterEntity entity) {
        return new ChapterDto(entity.getNumber(), entity.getTitle(), entity.getManga());
    }

    public static ChapterEntity toEntity(ChapterDto dto) {
        var entity = new ChapterEntity();
        entity.setNumber(dto.getNumber());
        entity.setTitle(dto.getTitle());
        entity.setManga(dto.getManga());
        return entity;
    }
}
