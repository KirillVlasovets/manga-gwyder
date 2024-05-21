package com.gorstreller.mangagwyder;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(name = "МангаГвайдер", shortName = "МангаГвайдер")
public class MangaGwyderApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(MangaGwyderApplication.class, args);
    }
}
