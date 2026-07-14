package com.cherrybite.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.service.StorageService;

@Service
@Profile("prod")
public class S3StorageService implements StorageService {
  private static final Logger log = LoggerFactory.getLogger(S3StorageService.class);

  @Override
  public String upload(MultipartFile file, String folderName) {
    log.info("Upload Image to S3 Storage");
    return null;
  }

  @Override
  public void delete(String imageUrl) {
    // TODO Auto-generated method stub

  }
}
