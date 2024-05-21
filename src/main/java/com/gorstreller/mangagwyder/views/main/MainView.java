package com.gorstreller.mangagwyder.views.main;

import com.gorstreller.mangagwyder.views.base.BaseLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "")
@RouteAlias(value = "")
@CssImport("./styles/shared-styles.css")
public class MainView extends BaseLayout {

    public MainView() {
        super();

        // Основное содержание
        VerticalLayout mainContent = createMainContent();
        add(mainContent);

        addFooter();
    }

    private VerticalLayout createMainContent() {
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.addClassName("main-content");

        // Слайдер с новинками и популярными мангами
        Div slider = createSlider();
        mainContent.add(slider);

        // Секция "Популярное"
        Div popularSection = createPopularSection();
        mainContent.add(popularSection);

        // Секция "Новые Выпуски"
        Div newReleasesSection = createNewReleasesSection();
        mainContent.add(newReleasesSection);

        // Секция "Рекомендуемое"
        Div recommendedSection = createRecommendedSection();
        mainContent.add(recommendedSection);

        return mainContent;
    }

    private Div createSlider() {
        Div slider = new Div();
        slider.addClassName("slider");

        // Пример изображения в слайдере
        Image slideImage = new Image("images/slider1.jpg", "Новинка 1");
        slideImage.addClassName("slide-image");
        slider.add(slideImage);

        return slider;
    }

    private Div createPopularSection() {
        Div popularSection = new Div();
        popularSection.addClassName("popular-section");

        NativeLabel popularTitle = new NativeLabel("Популярное");
        popularSection.add(popularTitle);

        // Пример популярных манг
        HorizontalLayout popularManga = new HorizontalLayout();
        for (int i = 1; i <= 4; i++) {
            Image mangaImage = new Image("images/popular" + i + ".jpg", "Популярная манга " + i);
            mangaImage.addClassName("manga-image");
            popularManga.add(mangaImage);
        }
        popularSection.add(popularManga);

        return popularSection;
    }

    private Div createNewReleasesSection() {
        Div newReleasesSection = new Div();
        newReleasesSection.addClassName("new-releases-section");

        NativeLabel newReleasesTitle = new NativeLabel("Новые Выпуски");
        newReleasesSection.add(newReleasesTitle);

        // Пример новых выпусков
        VerticalLayout newReleasesList = new VerticalLayout();
        for (int i = 1; i <= 4; i++) {
            HorizontalLayout release = new HorizontalLayout();
            Image releaseImage = new Image("images/new" + i + ".jpg", "Новая глава " + i);
            releaseImage.addClassName("release-image");
            NativeLabel releaseInfo = new NativeLabel("Глава " + i + " - Дата выхода: 2024-05-" + (10 + i));
            release.add(releaseImage, releaseInfo);
            newReleasesList.add(release);
        }
        newReleasesSection.add(newReleasesList);

        return newReleasesSection;
    }

    private Div createRecommendedSection() {
        Div recommendedSection = new Div();
        recommendedSection.addClassName("recommended-section");

        NativeLabel recommendedTitle = new NativeLabel("Рекомендуемое");
        recommendedSection.add(recommendedTitle);

        // Пример рекомендуемых манг
        HorizontalLayout recommendedManga = new HorizontalLayout();
        for (int i = 1; i <= 4; i++) {
            Image mangaImage = new Image("images/recommended" + i + ".jpg", "Рекомендуемая манга " + i);
            mangaImage.addClassName("manga-image");
            recommendedManga.add(mangaImage);
        }
        recommendedSection.add(recommendedManga);

        return recommendedSection;
    }
}
