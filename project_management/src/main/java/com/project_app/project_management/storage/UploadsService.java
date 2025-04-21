package com.project_app.project_management.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UploadsService {
    @Value("${upload.dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file)) {
            throw new  IOException("Invalid file type. Only JPG, JPEG or PNG files are allowed.");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);
        Files.write(filePath, file.getBytes());

        return filename;
    }

    private boolean isImageFile(MultipartFile file) {
        String original = file.getOriginalFilename();
        if (original == null) {
            return false;
        }
        String ext = Objects.requireNonNull(StringUtils.getFilenameExtension(original)).toLowerCase();
        return ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png");
    }
    public byte[] getFile(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(filename);
        return Files.readAllBytes(filePath);
    }
}
