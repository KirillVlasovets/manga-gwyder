package com.gorstreller.mangagwyder.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.gorstreller.mangagwyder.entity.model.Manga;
import com.gorstreller.mangagwyder.entity.model.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public S3Object getFile(String keyName) {
        return s3Client.getObject(bucketName, keyName);
    }

    public void uploadFile(String keyName, MultipartFile file) throws IOException {
        var putObjectResult = s3Client.putObject(bucketName, keyName, file.getInputStream(), null);
        log.info(putObjectResult.getMetadata());
    }

    public void deleteFile(String keyName) {
        s3Client.deleteObject(bucketName, keyName);
    }

    public List<Page> getAllPagesFromChapter(Manga manga, int chapterNumber) {
        String mangaTitle = manga.getTitle();
        List<Page> pages = new ArrayList<>();
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(String.format("%s/Chapters/%d", mangaTitle, chapterNumber));
        ListObjectsV2Result listing = s3Client.listObjectsV2(request);
        int pageNumber = 0; // count starts from 0 cause the first page will be deleted
        for (S3ObjectSummary summary : listing.getObjectSummaries()) {
            pages.add(Page.builder()
                    .path(summary.getKey())
                    .number(pageNumber++)
                    .build());
        }
        pages.removeFirst(); // removing first page with path related to pages folder
        return pages;
    }
}
