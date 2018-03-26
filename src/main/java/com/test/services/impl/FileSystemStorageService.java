package com.test.services.impl;

import com.test.common.exceptions.storage.StorageException;
import com.test.properties.StorageProperties;
import com.test.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileSystemStorageService implements StorageService {

    private Logger logger;

    @Autowired
    Logger getLogger() {
        return logger;
    }

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        init();
    }

    private void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String save(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

            Path path = this.rootLocation.resolve(filename);
            Files.copy(file.getInputStream(), path,
                    StandardCopyOption.REPLACE_EXISTING);
            return path.toFile().getAbsolutePath();
        } catch (IOException e) {
            throw new StorageException("Failed to save file " + filename, e);
        }
    }

    @Override
    public Path get(String fileLink) {
        return this.rootLocation.resolve(fileLink);
    }

    @Override
    public void delete(String filePath) {
        try {
            Files.delete(this.rootLocation.resolve(filePath));
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to delete file " + filePath);
        }
    }
}
