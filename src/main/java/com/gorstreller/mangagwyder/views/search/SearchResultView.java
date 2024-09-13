package com.gorstreller.mangagwyder.views.search;

import com.gorstreller.mangagwyder.service.MangaService;
import com.gorstreller.mangagwyder.utils.UrlUtils;
import com.gorstreller.mangagwyder.views.base.BaseLayout;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route("/search/:search_text")
@AnonymousAllowed
public class SearchResultView extends BaseLayout implements BeforeEnterObserver {

    private final MangaService mangaService;
    private final UrlUtils urlUtils;
    private final VerticalLayout allSearchResultsLayout = new VerticalLayout();

    @Autowired
    public SearchResultView(MangaService mangaService, UrlUtils urlUtils) {
        this.mangaService = mangaService;
        this.urlUtils = urlUtils;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        var searchTextParam = event.getRouteParameters().get("search_text");
        addSearchResults(searchTextParam);
    }

    private void addSearchResults(Optional<String> searchTextParam) {
        allSearchResultsLayout.removeAll();
        var foundMangas = mangaService.getMangaListByTitleContains(searchTextParam.get());
        if (foundMangas.isEmpty()) {
            allSearchResultsLayout.add(new NativeLabel("""
                    No such manga found :(
                    But don't despair and try again, changing your query a little :)
                    """));
        } else {
            for (var manga : foundMangas) {
                // Manga preview image
                var coverImage = new Image();
                coverImage.setSrc(urlUtils.createLogoPath(getBaseUrl(), manga.getTitle()));
                coverImage.setWidth(300, Unit.PIXELS);
                coverImage.setHeight(400, Unit.PIXELS);
                // Manga title
                var titleLabel = new NativeLabel();
                titleLabel.setText(manga.getTitle());
                titleLabel.setHeight(30, Unit.PIXELS);
                // Manga description
                var descriptionLabel = new NativeLabel();
                descriptionLabel.setText(manga.getDescription());
                descriptionLabel.setHeight(24, Unit.PIXELS);
                // Vertical layout
                var verticalLayout = new VerticalLayout();
                verticalLayout.add(titleLabel, descriptionLabel);
                // Horizontal layout
                var horizontalLayout = new HorizontalLayout();
                horizontalLayout.setClassName("list-element");
                horizontalLayout.add(coverImage, verticalLayout);

                allSearchResultsLayout.add(horizontalLayout);
            }
        }
        add(allSearchResultsLayout);
    }
}
