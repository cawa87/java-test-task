package com.test.services.impl;

import com.test.entities.Account;
import com.test.entities.File;
import com.test.repositories.FileRepository;
import com.test.services.FileService;
import com.test.services.StorageService;
import com.test.services.base.impl.DefaultBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Collection;

@Service
public class DefaultFileService extends DefaultBaseService<File> implements FileService {

    private final FileRepository fileRepository;
    private final StorageService storageService;

    @Autowired
    public DefaultFileService(FileRepository fileRepository, StorageService storageService) {
        super(fileRepository);
        this.fileRepository = fileRepository;
        this.storageService = storageService;
    }

    @Override
    public Page<File> getAll(Pageable pageRequest) {
        return fileRepository.findAll(pageRequest);
    }

    @Override
    public File save(MultipartFile file) {
        Account creator = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String filePath = storageService.save(file);
        File fileEntity = new File();
        fileEntity.setFilePath(filePath);
        fileEntity.setFileName(file.getName());
        fileEntity.setCreator(creator);
        return create(fileEntity);
    }

    @Override
    public void delete(Long id) {
        File file = get(id);
        storageService.delete(file.getFilePath());
        super.delete(id);
    }

    @Override
    public Path getFilePath(Long id) {
        File file = get(id);
        return  storageService.get(file.getFilePath());
    }

    @Override
    public void delete(Collection<File> files) {
        files.stream().map(File::getId).forEach(this::delete);
    }
}
