package com.gorstreller.mangagwyder.repository;

import com.gorstreller.mangagwyder.entity.model.MangaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaRepository extends JpaRepository<MangaEntity, Long>, PagingAndSortingRepository<MangaEntity, Long> {

    MangaEntity findMangaById(Long id);

    @Override

    Page<MangaEntity> findAll(Pageable pageable);

    MangaEntity findMangaByTitleIgnoreCase(String title);

    List<MangaEntity> findMangaByTitleContainsIgnoreCase(String titlePart);
}
