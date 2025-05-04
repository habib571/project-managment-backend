package com.project_app.project_management.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/images")
public class UploadsController {

    private final Path uploadPath;
    private static final Logger log = LoggerFactory.getLogger(UploadsController.class);

    public UploadsController(@Value("${upload.dir}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();
    }

    @GetMapping("/{filename}")
    public ResponseEntity<UrlResource> getImage(@PathVariable String filename) {
        try {
            Path filePath = uploadPath.resolve(filename).normalize();
            if (!filePath.startsWith(uploadPath)) {
                log.warn("Attempt to access file outside upload dir: {}", filename);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            UrlResource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                log.info("File not found or not readable: {}", filePath);
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                    .body(resource);

        } catch (IOException e) {
            log.error("Error serving file {}: ", filename, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
