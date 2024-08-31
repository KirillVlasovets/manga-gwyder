package com.gorstreller.mangagwyder.views.base;

import com.gorstreller.mangagwyder.service.AuthService;
import com.gorstreller.mangagwyder.service.s3.S3Service;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.login.LoginForm;
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
    private HorizontalLayout header = new HorizontalLayout();

    public BaseLayout() {
        add(header);
        updateHeader();
    }

    protected void addFooter() {
        // Нижний колонтитул
        Div footer = createFooter();
        add(footer);
    }

    private void updateHeader() {
        header.removeAll();
        header.addClassName("header");

        // Логотип и название сайта
        var logo = new Image("/frontend/images/logo.png", "Manga Logo");
        logo.addClassName("logo");
        var anchorLogo = new Anchor("", logo);
        var siteName = new NativeLabel("Манга-Читалка");
        siteName.addClassName("site-name");
        var anchorSiteName = new Anchor("", siteName);

        // Навигационное меню
        var homeLink = new Anchor("", "Главная");
        var genresLink = new Anchor("genres", "Жанры");
        var newReleasesLink = new Anchor("new-titles", "Новинки");
        var topLink = new Anchor("", "Топ");
        var recommendedLink = new Anchor("", "Рекомендуемое");
        var searchLink = new Anchor("", "Поиск");

        var loginDiv = new Div();
        if (AuthService.isUserLoggedIn()) {
            var username = AuthService.getCurrentUser();
            var logoutLink = new Anchor("login", "Выйти");
            loginDiv.add(new NativeLabel(String.format("Welcome aboard, %s!", username)));
            loginDiv.add(logoutLink);
        } else {
            var loginLink = new Anchor("", "Вход/Регистрация");
            var loginDialog = createLoginDialog();
            loginLink.getElement().addEventListener("click", event -> loginDialog.open());

            loginDiv.add(new NativeLabel("Welcome to the Manga Chitalka, Stranger!"));
            loginDiv.add(loginLink);
        }

        header.add(anchorLogo, anchorSiteName, homeLink, genresLink, newReleasesLink, topLink, recommendedLink, searchLink, loginDiv);
        setSpacing(false);
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

    private Dialog createLoginDialog() {
        var loginDialog = new Dialog();
        loginDialog.setCloseOnOutsideClick(true);

        var login = new LoginForm();
        login.setAction("login");

        add(
                new H1("Manga-Chitalka app"),
                login
        );
        var dialogLayout = new VerticalLayout();
        dialogLayout.add(login);
        loginDialog.add(dialogLayout);
        return loginDialog;
    }

    private Div createFooter() {
        var footer = new Div();
        footer.addClassName("footer");

        var footerText = new NativeLabel("© 2024 Манга-Читалка. Все права защищены.");
        footer.add(footerText);

        // Социальные сети
        var socialMediaLinks = new HorizontalLayout();
        var facebookLink = new Anchor("https://facebook.com", "Facebook");
        Anchor twitterLink = new Anchor("https://twitter.com", "Twitter");
        Anchor instagramLink = new Anchor("https://instagram.com", "Instagram");

        socialMediaLinks.add(facebookLink, twitterLink, instagramLink);
        footer.add(socialMediaLinks);

        return footer;
    }

    protected void changeURLHashForPages(Integer pageNumber) {
        getUI().ifPresent(ui -> ui.getPage().executeJs(String.format("window.location.hash='%d';", pageNumber)));
    }
}
