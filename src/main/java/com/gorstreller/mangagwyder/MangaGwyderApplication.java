package com.gorstreller.mangagwyder;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.gorstreller.mangagwyder.service.s3.S3Service;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@EntityScan("com.gorstreller.mangagwyder.entity.model")
@SpringBootApplication
@PWA(name = "Манга-Читалка", shortName = "Манга-Читалка")
@Log4j2
public class MangaGwyderApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(MangaGwyderApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(S3Service s3Service) {
        return args -> {
            log.info("Spring Boot AWS S3 integration...");

            try {
                var s3Object = s3Service.getFile("jvm.jpg");
                log.info(s3Object.getKey());
            } catch (AmazonS3Exception e) {
                log.error(e.getMessage());
            }
        };
    }
}
