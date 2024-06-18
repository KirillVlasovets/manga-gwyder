package com.gorstreller.mangagwyder.controller;

import com.gorstreller.mangagwyder.entity.model.Manga;
import com.gorstreller.mangagwyder.service.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("${api.prefix}")
public class MangaController {

    private final MangaService mangaService;

    @Autowired
    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping("/all-titles")
    @Operation(summary = "Find all manga titles")
    public Page<Manga> findMangaPage(@RequestParam(defaultValue = "1")Integer page,
                                     @RequestParam(defaultValue = "5")Integer size) {
        return mangaService.findAllPaginated(PageRequest.of(page - 1, size));
    }
}
