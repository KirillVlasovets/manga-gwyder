package com.gorstreller.mangagwyder.dto.wrapper;

import com.gorstreller.mangagwyder.dto.model.MangaDto;
import com.gorstreller.mangagwyder.entity.model.MangaEntity;

public class MangaWrapper {

    public static MangaDto toDto(MangaEntity entity) {
        return entity == null ? null : new MangaDto(entity.getId(), entity.getTitle(), entity.getDescription());
    }

    public static MangaEntity toEntity(MangaDto dto) {
        if (dto == null) {
            return null;
        }
        var entity = new MangaEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
