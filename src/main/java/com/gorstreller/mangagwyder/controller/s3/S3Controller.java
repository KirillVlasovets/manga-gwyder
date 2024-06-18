package com.gorstreller.mangagwyder.controller.s3;

import com.gorstreller.mangagwyder.service.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("${s3.prefix}")
public class S3Controller {

    private final S3Service s3Service;

    @Autowired
    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping
    public String health() {
        return "OK";
    }

    @GetMapping("view")
    public ResponseEntity<byte[]> download(@RequestParam String path) {
        var s3Object = s3Service.getFile(path);

        try (InputStream inputStream = s3Object.getObjectContent()) {
            var bytes = inputStream.readAllBytes();

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + path + "\"")
                    .body(bytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/upload/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        s3Service.uploadFile(file.getOriginalFilename(), file);
        return "File uploaded successfully";
    }

    @PostMapping(value = "/upload/directory",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadDirectory(@RequestParam("directory") String path, @RequestParam("file") MultipartFile file) throws IOException {
        s3Service.uploadFile(path + "/" + file.getOriginalFilename(), file);
        return "File uploaded successfully";
    }

    @DeleteMapping("delete/path")
    public String deleteFile(@RequestParam("path") String path) {
        s3Service.deleteFile(path);
        return "File deleted successfully";
    }
}
