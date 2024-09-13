package com.gorstreller.mangagwyder.views.manga;

import com.gorstreller.mangagwyder.constants.UserRoles;
import com.gorstreller.mangagwyder.dto.model.MangaDto;
import com.gorstreller.mangagwyder.entity.model.ChapterEntity;
import com.gorstreller.mangagwyder.views.model.Page;
import com.gorstreller.mangagwyder.service.ChaptersService;
import com.gorstreller.mangagwyder.service.MangaService;
import com.gorstreller.mangagwyder.views.base.BaseLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Route(value = "reader/:title/:chapterNumber")
@RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
public class ReaderView extends BaseLayout implements BeforeEnterObserver {

    @Autowired
    private MangaService mangaService;
    @Autowired
    private ChaptersService chaptersService;

    private ChapterEntity currentChapterEntity;
    private MangaDto currentManga;
    private List<Page> pages;
    private int currentPageIndex;

    private final Image mangaPageImage = new Image();
    private final VerticalLayout pageSelectionLayout = new VerticalLayout();
    private final Button prevChapterButton = new Button("Previous Chapter");
    private final Button nextChapterButton = new Button("Next Chapter");

    public ReaderView() {
        mangaPageImage.setWidth("100%");
        mangaPageImage.addClickListener(e -> goToNextPage());

        var chapterSelectionLayout = new HorizontalLayout();
        chapterSelectionLayout.add(prevChapterButton, nextChapterButton);
        add(pageSelectionLayout, mangaPageImage, chapterSelectionLayout);
        updateChapterNavigationButtons();

        // Keyboard navigation
        getElement().addEventListener("keydown", e -> goToPreviousPage()).setFilter("event.key == 'ArrowLeft'");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> chapterNumberParam = event.getRouteParameters().get("chapterNumber");
        Optional<String> titleParam = event.getRouteParameters().get("title");

        if (chapterNumberParam.isPresent()) {
            int chapterNumber = Integer.parseInt(chapterNumberParam.get());
            String title = titleParam.get();
            currentManga = mangaService.getMangaByTitle(title);
            currentChapterEntity = chaptersService.getChapterByNumberAndMangaId(chapterNumber, currentManga.getId());
            pages = s3Service.getAllPagesFromChapter(currentManga, chapterNumber);
            currentPageIndex = 0;

            if (pages.isEmpty()) {
                add(new NativeLabel("Chapter not found or no pages available"));
                return;
            }

            updatePageSelection();
            updatePage();
        }
    }

    private void updatePage() {
        if (currentPageIndex >= 0 && currentPageIndex < pages.size()) {
            Page currentPage = pages.get(currentPageIndex);
            mangaPageImage.setSrc(getBaseUrl() + s3Prefix + String.format("/view?path=%s",
                    currentPage.getPath()));
        }
    }

    private void updatePageSelection() {
        pageSelectionLayout.removeAll();
        HorizontalLayout pageSelectionLayoutRow = new HorizontalLayout();
        for (int i = 0; i < pages.size(); i++) {
            Button pageButton = new Button(String.valueOf(pages.get(i).getNumber()));
            int finalI = i;
            pageButton.addClickListener(e -> {
                currentPageIndex = finalI;
                updatePage();
            });

            // Highlight the current page button
            if (i == currentPageIndex) {
                pageButton.getStyle().set("background-color", "#E6E6FA"); // бледно-фиолетовый цвет
            }

            pageSelectionLayoutRow.add(pageButton);

            // Перенос на следующую строку через каждые 15 страниц
            if ((i + 1) % 15 == 0) {
                pageSelectionLayout.add(pageSelectionLayoutRow);
                pageSelectionLayoutRow = new HorizontalLayout();
            }
        }
        pageSelectionLayout.add(pageSelectionLayoutRow);
    }

    private void updateChapterNavigationButtons() {
        prevChapterButton.addClickListener(e -> navigateToChapter(currentChapterEntity.getNumber() - 1));
        nextChapterButton.addClickListener(e -> navigateToChapter(currentChapterEntity.getNumber() + 1));
    }

    private void navigateToChapter(int chapterNumber) {
        Optional<ChapterEntity> optionalChapter = Optional.ofNullable(chaptersService.getChapterByNumberAndMangaId(chapterNumber, currentManga.getId()));
        if (optionalChapter.isPresent()) {
            getUI().ifPresent(ui -> ui.navigate(ReaderView.class, new RouteParameters(new RouteParam("title", currentManga.getTitle()),
                    new RouteParam("chapterNumber", chapterNumber))));
            currentChapterEntity = chaptersService.getChapterByNumberAndMangaId(chapterNumber, currentManga.getId());

            getElement().executeJs("window.scrollTo(0, 0);");
        }
    }

    private void goToNextPage() {
        if (currentPageIndex < pages.size() - 1) {
            currentPageIndex++;
            updatePage();
            getElement().executeJs("window.scrollTo(0, 0);");
        } else {
            navigateToChapter(currentChapterEntity.getNumber() + 1);
        }
    }

    private void goToPreviousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            updatePage();
            getElement().executeJs("window.scrollTo(0, 0);");
        } else {
            navigateToChapter(currentChapterEntity.getNumber() - 1);
        }
    }
}
