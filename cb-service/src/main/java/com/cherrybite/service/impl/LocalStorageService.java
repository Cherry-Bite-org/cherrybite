package com.cherrybite.service.impl;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.service.StorageService;

@Service
@Profile("local")
public class LocalStorageService implements StorageService {
  private static final Logger log = LoggerFactory.getLogger(LocalStorageService.class);

  @Value("${app.upload.dir}")
  private String uploadDir;

  @Value("${app.base-url}")
  private String baseUrl;

  @Value("${server.servlet.context-path}")
  private String contextPath;

  @Override
  public String upload(MultipartFile file, String folderName) {
    log.info("Upload Image to Local Storage");
    try {

      String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

      Path path = Paths.get(uploadDir, folderName);

      if (!Files.exists(path)) {
        Files.createDirectories(path);
      }

      Files.copy(file.getInputStream(), path.resolve(fileName),
          StandardCopyOption.REPLACE_EXISTING);

      return baseUrl + contextPath + "/uploads/" + folderName + "/" + fileName;
    } catch (IOException e) {
      throw new RuntimeException("Image upload failed", e);
    }

  }

  @Override
  public void delete(String imageUrl) {
    if (imageUrl == null || imageUrl.isBlank()) {
      return;
    }

    log.info("Delete existing image from the local storage");
    try {

      String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

      Path path = Paths.get(uploadDir, fileName);

      Files.deleteIfExists(path);

    } catch (IOException e) {
      throw new RuntimeException("Unable to delete image", e);
    }

  }

}
