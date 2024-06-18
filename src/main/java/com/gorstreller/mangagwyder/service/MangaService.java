package com.gorstreller.mangagwyder.service;

import com.gorstreller.mangagwyder.entity.model.Manga;
import com.gorstreller.mangagwyder.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Manga findOne(Long id) {
        return mangaRepository.findMangaById(id);
    }
}
