package com.gorstreller.mangagwyder.repository;

import com.gorstreller.mangagwyder.entity.model.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChaptersRepository extends JpaRepository<ChapterEntity, Long> {

    List<ChapterEntity> findByMangaId(Long mangaId);

    ChapterEntity findByNumberAndMangaId(Integer number, Long mangaId);
}
