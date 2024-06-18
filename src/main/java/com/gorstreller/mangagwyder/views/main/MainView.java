package com.gorstreller.mangagwyder.views.main;

import com.gorstreller.mangagwyder.service.MangaService;
import com.gorstreller.mangagwyder.utils.PropertiesUtils;
import com.gorstreller.mangagwyder.views.base.BaseLayout;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Route(value = "")
@RouteAlias(value = "")
@CssImport("./styles/shared-styles.css")
public class MainView extends BaseLayout {

    private final MangaService mangaService;
    private final String s3Prefix = "/api/v1";

    @Autowired
    public MainView(MangaService mangaService) {
        super();
        this.mangaService = mangaService;

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
        String imageUrl = getBaseUrl() + s3Prefix;
        for (int i = 1; i <= 4; i++) {
            var mangaTitle = mangaService.findOne((long) i).getTitle();
            Image mangaImage = new Image(imageUrl + String.format("/view?path=%s/%s",
                    mangaTitle,
                    String.format("%s_Logo.jpg", mangaTitle.replace(" ", "_"))), mangaTitle);
            mangaImage.addClassName("manga-image");
            mangaImage.setHeight(200, Unit.PIXELS);
            mangaImage.setWidth(150, Unit.PIXELS);
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
