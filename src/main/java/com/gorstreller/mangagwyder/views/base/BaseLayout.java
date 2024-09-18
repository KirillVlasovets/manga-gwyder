package com.gorstreller.mangagwyder.views.base;

import com.gorstreller.mangagwyder.service.s3.S3Service;
import com.gorstreller.mangagwyder.utils.SecurityUtils;
import com.gorstreller.mangagwyder.views.login.LoginView;
import com.gorstreller.mangagwyder.views.search.SearchResultView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

@AnonymousAllowed
public class BaseLayout extends VerticalLayout implements RouterLayout {

    @Autowired
    protected S3Service s3Service;
    protected final String s3Prefix = "/api/v1";
    private final HorizontalLayout header = new HorizontalLayout();

    public BaseLayout() {
        add(header);
        updateHeader();
    }

    protected void addFooter() {
        // Footer
        Div footer = createFooter();
        add(footer);
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

    private void updateHeader() {
        header.removeAll();
        header.addClassName("header");

        // Logo and app name
        var logo = new Image("/frontend/images/logo.png", "Manga Logo");
        logo.addClassName("logo");
        var anchorLogo = new Anchor("", logo);
        var siteName = new NativeLabel("Manga-Chitalka");
        siteName.addClassName("site-name");
        var anchorSiteName = new Anchor("", siteName);

        // Navigation menu
        var homeLink = new Anchor("", "Main Page");
        var genresLink = new Anchor("genres", "Genres");
        var newReleasesLink = new Anchor("new-titles", "New Titles");
        var topLink = new Anchor("", "TOP");
        var recommendedLink = new Anchor("", "Recommendations");
        var searchTextField = new TextField("Search");
        var searchButton = new Button(new Icon(VaadinIcon.SEARCH));
        var greetingText = getGreetingText();
        var loginLogoutButton = getLoginLogoutButton();
        //TODO: implement global search
//        var searchLink = new Anchor("", "Поиск");

        // Set search text area and button
        searchTextField.setClearButtonVisible(true);
        searchTextField.setValueChangeMode(ValueChangeMode.ON_CHANGE);
        searchTextField.addKeyDownListener(Key.ENTER, event ->
                UI.getCurrent().navigate(SearchResultView.class, new RouteParam("search_text", searchTextField.getValue())));
        searchButton.addClickListener(event ->
            UI.getCurrent().navigate(SearchResultView.class, new RouteParam("search_text", searchTextField.getValue()))
        );

        header.add(anchorLogo,
                anchorSiteName,
                homeLink,
                genresLink,
                newReleasesLink,
                topLink,
                recommendedLink,
                searchTextField,
                searchButton,
                greetingText,
                loginLogoutButton
//                searchLink
        );

        setSpacing(false);
    }

    private NativeLabel getGreetingText() {
        var greetingText = new NativeLabel();

        // Setting greeting text and visibility for logout and login buttons
        if (SecurityUtils.isUserLoggedIn()) {
            greetingText.setText(String.format("Welcome, %s", SecurityUtils.getNameOfAuthenticatedUser()));
        } else {
            greetingText.setText("Welcome, Stranger!");
        }
        return greetingText;
    }

    private Button getLoginLogoutButton() {
        var loginLogoutButton = new Button();
        if (SecurityUtils.isUserLoggedIn()) {
            loginLogoutButton.setText("Logout");
            loginLogoutButton.setIcon(VaadinIcon.SIGN_OUT.create());
            loginLogoutButton.addClickListener(click -> {
                VaadinSession.getCurrent().getSession().invalidate();
                UI.getCurrent().getPage().reload();
            });
        } else {
            loginLogoutButton.setText("Log In");
            loginLogoutButton.setIcon(VaadinIcon.SIGN_IN.create());
            loginLogoutButton.addClickListener(click -> UI.getCurrent().navigate(LoginView.class));
        }
        return loginLogoutButton;
    }

    private Div createFooter() {
        var footer = new Div();
        footer.addClassName("footer");

        var footerText = new NativeLabel("© 2024 Manga-Chitalka. All rights reserved.");
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
}
