package com.test.services;

import com.test.entities.File;
import com.test.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Collection;

public interface FileService extends BaseService<File> {

    Page<File> getAll(Pageable pageRequest);

    File save(MultipartFile file);

    Path getFilePath(Long id);

    void delete(Collection<File> files);
}
