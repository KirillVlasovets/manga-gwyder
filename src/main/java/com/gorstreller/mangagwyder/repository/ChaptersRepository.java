package com.gorstreller.mangagwyder.repository;

import com.gorstreller.mangagwyder.entity.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChaptersRepository extends JpaRepository<Chapter, Long> {

    List<Chapter> findByMangaId(Long mangaId);

    Chapter findByNumberAndMangaId(Integer number, Long mangaId);
}
