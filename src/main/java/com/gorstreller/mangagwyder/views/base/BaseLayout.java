package com.gorstreller.mangagwyder.views.base;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class BaseLayout extends VerticalLayout {

    public BaseLayout() {
        // Верхний колонтитул
        HorizontalLayout header = createHeader();
        add(header);
    }

    protected void addFooter() {
        // Нижний колонтитул
        Div footer = createFooter();
        add(footer);
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");

        // Логотип и название сайта
        Image logo = new Image("", "МангаГвайдер");
        logo.addClassName("logo");
        NativeLabel siteName = new NativeLabel("МангаГвайдер");
        siteName.addClassName("site-name");

        // Навигационное меню
        Anchor homeLink = new Anchor("", "Главная");
        Anchor genresLink = new Anchor("genres", "Жанры");
        Anchor newReleasesLink = new Anchor("", "Новинки");
        Anchor topLink = new Anchor("", "Топ");
        Anchor recommendedLink = new Anchor("", "Рекомендуемое");
        Anchor searchLink = new Anchor("", "Поиск");
        Anchor loginLink = new Anchor("", "Вход/Регистрация");

        header.add(logo, siteName, homeLink, genresLink, newReleasesLink, topLink, recommendedLink, searchLink, loginLink);
        return header;
    }

    private Div createFooter() {
        Div footer = new Div();
        footer.addClassName("footer");

        NativeLabel footerText = new NativeLabel("© 2024 МангаГвайдер. Все права защищены.");
        footer.add(footerText);

        // Социальные сети
        HorizontalLayout socialMediaLinks = new HorizontalLayout();
        Anchor facebookLink = new Anchor("https://facebook.com", "Facebook");
        Anchor twitterLink = new Anchor("https://twitter.com", "Twitter");
        Anchor instagramLink = new Anchor("https://instagram.com", "Instagram");

        socialMediaLinks.add(facebookLink, twitterLink, instagramLink);
        footer.add(socialMediaLinks);

        return footer;
    }
}
