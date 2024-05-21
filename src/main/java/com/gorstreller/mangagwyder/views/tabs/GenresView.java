package com.gorstreller.mangagwyder.views.tabs;

import com.gorstreller.mangagwyder.views.base.BaseLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("genres")
@CssImport("./styles/shared-styles.css")
public class GenresView extends BaseLayout {

    public GenresView() {
        super();

        // Заголовок
        NativeLabel header = new NativeLabel("Жанры");
        header.addClassName("page-title");
        add(header);

        // Список жанров
        VerticalLayout genresList = new VerticalLayout();
        genresList.addClassName("genres-list");

        String[] genres = {"Приключения", "Фэнтези", "Романтика", "Комедия", "Драма", "Мистика", "Экшен", "Сёнэн", "Сэйнэн"};

        for (String genre : genres) {
            Anchor genreLink = new Anchor("#", genre);
            genreLink.addClassName("genre-link");
            genresList.add(genreLink);
        }

        add(genresList);

        addFooter();
    }
}
