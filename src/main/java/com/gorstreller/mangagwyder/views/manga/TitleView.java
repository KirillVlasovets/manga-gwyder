package com.gorstreller.mangagwyder.views.manga;

import com.gorstreller.mangagwyder.constants.UserRoles;
import com.gorstreller.mangagwyder.entity.model.ChapterEntity;
import com.gorstreller.mangagwyder.service.ChaptersService;
import com.gorstreller.mangagwyder.service.MangaService;
import com.gorstreller.mangagwyder.utils.UrlUtils;
import com.gorstreller.mangagwyder.views.base.BaseLayout;
import com.vaadin.flow.component.Unit;
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

    private final MangaService mangaService;
    private final ChaptersService chaptersService;
    private final UrlUtils urlUtils;

    private final NativeLabel titleLabel = new NativeLabel();
    private final NativeLabel descriptionLabel = new NativeLabel();
    private final VerticalLayout chapterListLayout = new VerticalLayout();
    private final Image coverImage = new Image();
    private String mangaTitle;

    @Autowired
    public TitleView(MangaService mangaService, ChaptersService chaptersService, UrlUtils urlUtils) {
        this.mangaService = mangaService;
        this.chaptersService = chaptersService;
        this.urlUtils = urlUtils;
        add(coverImage, titleLabel, descriptionLabel, chapterListLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> mangaTitleParam = event.getRouteParameters().get("title");

        if (mangaTitleParam.isPresent()) {
            mangaTitle = mangaTitleParam.get();
            var manga = mangaService.getMangaByTitle(mangaTitle);

            if (manga == null) {
                add(new NativeLabel("Manga not found"));
                return;
            }

            coverImage.setSrc(urlUtils.createLogoPath(getBaseUrl(), mangaTitle));
            coverImage.setAlt("Cover Image");
            coverImage.setWidth(300, Unit.PIXELS);
            coverImage.setHeight(450, Unit.PIXELS);

            titleLabel.setText(manga.getTitle());
            titleLabel.getStyle().set("font-size", "24px").set("font-weight", "bold");

            descriptionLabel.setText(manga.getDescription());

            chapterListLayout.removeAll();
            var chapters = chaptersService.getChaptersByMangaId(manga.getId());
            for (int i = 0; i < Math.min(10, chapters.size()); i++) {
                var chapter = chapters.get(i);
                var chapterLink = new RouterLink("Chapter " + chapter.getNumber() + ": " + chapter.getTitle(),
                        ReaderView.class, new RouteParameters(new RouteParam("title", mangaTitle),
                        new RouteParam("chapterNumber", chapter.getNumber())));
                chapterListLayout.add(chapterLink);
            }

            if (chapters.size() > 10) {
                var showMoreButton = getButton(chapters);
                chapterListLayout.add(showMoreButton);
            }

            // The first chapter link
            var readerLink = new RouterLink("Read First Chapter", ReaderView.class,
                    new RouteParameters(new RouteParam("title", mangaTitle),
                            new RouteParam("chapterNumber", chaptersService.getChaptersByMangaId(manga.getId())
                            .getFirst().getNumber().toString())));
            chapterListLayout.add(readerLink);
        }
    }

    private Button getButton(List<ChapterEntity> chapterEntities) {
        Button showMoreButton = new Button("Show More", event -> {
            for (int i = 10; i < chapterEntities.size(); i++) {
                ChapterEntity chapterEntity = chapterEntities.get(i);
                RouterLink chapterLink = new RouterLink("Chapter " + chapterEntity.getNumber() + ": " + chapterEntity.getTitle(),
                        ReaderView.class,  new RouteParameters(new RouteParam("title", mangaTitle),
                        new RouteParam("chapterNumber", chapterEntity.getNumber())));
                chapterListLayout.add(chapterLink);
            }
        });
        showMoreButton.setVisible(false);
        return showMoreButton;
    }
}
