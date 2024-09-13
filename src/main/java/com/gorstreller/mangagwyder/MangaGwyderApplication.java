package com.gorstreller.mangagwyder;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@EntityScan("com.gorstreller.mangagwyder.entity.model")
@SpringBootApplication
@PWA(name = "Манга-Читалка", shortName = "Манга-Читалка")
@EnableCaching
public class MangaGwyderApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(MangaGwyderApplication.class, args);
    }
}
