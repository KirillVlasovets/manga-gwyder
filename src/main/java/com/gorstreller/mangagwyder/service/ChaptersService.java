package com.gorstreller.mangagwyder.service;

import com.gorstreller.mangagwyder.entity.model.Chapter;
import com.gorstreller.mangagwyder.repository.ChaptersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChaptersService {

    private final ChaptersRepository chaptersRepository;

    @Autowired
    public ChaptersService(ChaptersRepository chaptersRepository) {
        this.chaptersRepository = chaptersRepository;
    }

    public List<Chapter> getChaptersByMangaId(Long mangaId) {
        return chaptersRepository.findByMangaId(mangaId);
    }

    public Chapter getChapterByNumberAndMangaId(Integer chapterNumber, Long mangaId) {
        return chaptersRepository.findByNumberAndMangaId(chapterNumber, mangaId);
    }
}
