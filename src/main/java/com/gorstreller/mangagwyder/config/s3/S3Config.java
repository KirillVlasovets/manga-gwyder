package com.gorstreller.mangagwyder.config.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.gorstreller.mangagwyder.service.s3.S3Service;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class S3Config {

    @Value("${aws.access.key}")
    private String awsAccessKey;

    @Value("${aws.secret.key}")
    private String awsSecretKey;

    @Bean
    public AmazonS3 s3client() {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.EU_CENTRAL_1)
                .enableAccelerateMode()
                .build();
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
