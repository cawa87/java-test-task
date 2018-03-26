package com.test.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    String save(MultipartFile file);

    Path get(String filePath);

    void delete(String filePath);
}
