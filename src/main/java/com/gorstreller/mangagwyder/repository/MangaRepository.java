package com.gorstreller.mangagwyder.repository;

import com.gorstreller.mangagwyder.entity.model.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long>, PagingAndSortingRepository<Manga, Long> {

    Manga findMangaById(Long id);

    @Override
    Page<Manga> findAll(Pageable pageable);

    Manga findMangaByTitleIgnoreCase(String title);
}
