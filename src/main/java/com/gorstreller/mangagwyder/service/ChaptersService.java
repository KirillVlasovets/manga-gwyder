package com.gorstreller.mangagwyder.service;

import com.gorstreller.mangagwyder.dto.model.ChapterDto;
import com.gorstreller.mangagwyder.dto.wrapper.ChapterWrapper;
import com.gorstreller.mangagwyder.entity.model.ChapterEntity;
import com.gorstreller.mangagwyder.repository.ChaptersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChaptersService {

    private final ChaptersRepository chaptersRepository;

    @Autowired
    public ChaptersService(ChaptersRepository chaptersRepository) {
        this.chaptersRepository = chaptersRepository;
    }

    @Cacheable(value = "chaptersByMangaId", key = "#mangaId")
    public List<ChapterEntity> getChaptersByMangaId(Long mangaId) {
        return chaptersRepository.findByMangaId(mangaId);
    }

    @Cacheable(value = "chapterByNumberAndMangaId", key = "{#chapterNumber, #mangaId}")
    public ChapterDto getChapterByNumberAndMangaId(Integer chapterNumber, Long mangaId) {
        return ChapterWrapper.toDto(chaptersRepository.findByNumberAndMangaId(chapterNumber, mangaId));
    }
}
