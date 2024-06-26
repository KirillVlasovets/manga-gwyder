package com.gorstreller.mangagwyder.views.base;

import com.gorstreller.mangagwyder.service.s3.S3Service;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseLayout extends VerticalLayout implements RouterLayout {

    @Autowired
    protected S3Service s3Service;
    protected final String s3Prefix = "/api/v1";

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
        var logo = new Image("/frontend/images/logo.png", "Manga Logo");
        logo.addClassName("logo");
        var anchorLogo = new Anchor("", logo);
        var siteName = new NativeLabel("Манга-Читалка");
        siteName.addClassName("site-name");
        var anchorSiteName = new Anchor("", siteName);

        // Навигационное меню
        Anchor homeLink = new Anchor("", "Главная");
        Anchor genresLink = new Anchor("genres", "Жанры");
        Anchor newReleasesLink = new Anchor("new-titles", "Новинки");
        Anchor topLink = new Anchor("", "Топ");
        Anchor recommendedLink = new Anchor("", "Рекомендуемое");
        Anchor searchLink = new Anchor("", "Поиск");
        Anchor loginLink = new Anchor("", "Вход/Регистрация");

        header.add(anchorLogo, anchorSiteName, homeLink, genresLink, newReleasesLink, topLink, recommendedLink, searchLink, loginLink);
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

    protected String getBaseUrl() {
        var request = VaadinService.getCurrentRequest();
        if (request instanceof VaadinServletRequest) {
            HttpServletRequest httpServletRequest = ((VaadinServletRequest) request).getHttpServletRequest();
            String scheme = httpServletRequest.getScheme();
            String serverName = httpServletRequest.getServerName();
            int serverPort = httpServletRequest.getServerPort();
            String contextPath = httpServletRequest.getContextPath();

            String baseUrl = scheme + "://" + serverName;
            if (serverPort != 80 && serverPort != 443) {
                baseUrl += ":" + serverPort;
            }
            baseUrl += contextPath;
            return baseUrl;
        }
        return null;
    }
}
