package com.gorstreller.mangagwyder.service;

import com.gorstreller.mangagwyder.dto.model.MangaDto;
import com.gorstreller.mangagwyder.dto.wrapper.MangaWrapper;
import com.gorstreller.mangagwyder.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MangaService {

    private final MangaRepository mangaRepository;

    @Autowired
    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    public Page<MangaDto> findAllPaginated(Pageable pageable) {
        var mangaEntities = mangaRepository.findAll(pageable);
        return mangaEntities.map(MangaWrapper::toDto);
    }

    public MangaDto getMangaById(Long id) {
        return MangaWrapper.toDto(mangaRepository.findMangaById(id));
    }

    public MangaDto getMangaByTitle(String title) {
        return MangaWrapper.toDto(mangaRepository.findMangaByTitleIgnoreCase(title));
    }

    public ArrayList<MangaDto> getMangaListByTitleContains(String titlePart) {
        return mangaRepository.findMangaByTitleContainsIgnoreCase(titlePart)
                .stream().map(MangaWrapper::toDto).collect(Collectors.toCollection(ArrayList::new));
    }
}
