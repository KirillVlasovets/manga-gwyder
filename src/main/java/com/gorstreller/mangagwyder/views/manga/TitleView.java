package com.gorstreller.mangagwyder.views.manga;

import com.gorstreller.mangagwyder.constants.UserRoles;
import com.gorstreller.mangagwyder.entity.model.Chapter;
import com.gorstreller.mangagwyder.entity.model.Manga;
import com.gorstreller.mangagwyder.service.ChaptersService;
import com.gorstreller.mangagwyder.service.MangaService;
import com.gorstreller.mangagwyder.utils.UrlUtils;
import com.gorstreller.mangagwyder.views.base.BaseLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Route(value = "manga/:title")
@RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
public class TitleView extends BaseLayout implements BeforeEnterObserver {

    @Autowired
    private MangaService mangaService;
    @Autowired
    private ChaptersService chaptersService;
    @Autowired
    private UrlUtils urlUtils;

    private final NativeLabel titleLabel = new NativeLabel();
    private final NativeLabel descriptionLabel = new NativeLabel();
    private final VerticalLayout chapterListLayout = new VerticalLayout();
    private final Image coverImage = new Image();
    private String mangaTitle;

    public TitleView() {
        add(coverImage, titleLabel, descriptionLabel, chapterListLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> mangaTitleParam = event.getRouteParameters().get("title");

        if (mangaTitleParam.isPresent()) {
            mangaTitle = mangaTitleParam.get();
            Manga manga = mangaService.getMangaByTitle(mangaTitle);

            if (manga == null) {
                add(new NativeLabel("Manga not found"));
                return;
            }

            coverImage.setSrc(urlUtils.createLogoPath(getBaseUrl(), mangaTitle));
            coverImage.setAlt("Cover Image");
            coverImage.setWidth("300px");
            coverImage.setHeight("450px");

            titleLabel.setText(manga.getTitle());
            titleLabel.getStyle().set("font-size", "24px").set("font-weight", "bold");

            descriptionLabel.setText(manga.getDescription());

            chapterListLayout.removeAll();
            List<Chapter> chapters = chaptersService.getChaptersByMangaId(manga.getId());
            for (int i = 0; i < Math.min(10, chapters.size()); i++) {
                Chapter chapter = chapters.get(i);
                RouterLink chapterLink = new RouterLink("Chapter " + chapter.getNumber() + ": " + chapter.getTitle(),
                        ReaderView.class, new RouteParameters(new RouteParam("title", mangaTitle),
                        new RouteParam("chapterNumber", chapter.getNumber())));
                chapterListLayout.add(chapterLink);
            }

            if (chapters.size() > 10) {
                Button showMoreButton = getButton(chapters);
                chapterListLayout.add(showMoreButton);
            }

            // Ссылка на читалку первой главы
            RouterLink readerLink = new RouterLink("Read First Chapter", ReaderView.class,
                    new RouteParameters(new RouteParam("title", mangaTitle),
                            new RouteParam("chapterNumber", chaptersService.getChaptersByMangaId(manga.getId())
                            .getFirst().getNumber().toString())));
            chapterListLayout.add(readerLink);
        }
    }

    private Button getButton(List<Chapter> chapters) {
        Button showMoreButton = new Button("Show More", e -> {
            for (int i = 10; i < chapters.size(); i++) {
                Chapter chapter = chapters.get(i);
                RouterLink chapterLink = new RouterLink("Chapter " + chapter.getNumber() + ": " + chapter.getTitle(),
                        ReaderView.class,  new RouteParameters(new RouteParam("title", mangaTitle),
                        new RouteParam("chapterNumber", chapter.getNumber())));
                chapterListLayout.add(chapterLink);
            }
        });
        showMoreButton.setVisible(false);
        return showMoreButton;
    }
}
