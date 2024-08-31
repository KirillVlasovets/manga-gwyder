package com.gorstreller.mangagwyder.service;

import com.gorstreller.mangagwyder.entity.model.Manga;
import com.gorstreller.mangagwyder.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MangaService {

    private final MangaRepository mangaRepository;

    @Autowired
    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    public Page<Manga> findAllPaginated(Pageable pageable) {
        return mangaRepository.findAll(pageable);
    }

    @Cacheable(value = "mangaById", key = "#id")
    public Manga getMangaById(Long id) {
        return mangaRepository.findMangaById(id);
    }

    @Cacheable(value = "mangaByTitle", key = "#title.toLowerCase()")
    public Manga getMangaByTitle(String title) {
        return mangaRepository.findMangaByTitleIgnoreCase(title);
    }
}
