package com.test.controllers;

import com.test.common.Paths;
import com.test.dtos.file.FileDto;
import com.test.dtos.file.FilePageRequest;
import com.test.entities.File;
import com.test.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;


@RestController
@RequestMapping(Paths.FILES)
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public Page<FileDto> getAllFiles(@RequestParam(required = false) FilePageRequest pageRequest) {
        if (null == pageRequest) {
            pageRequest = new FilePageRequest();
        }
        return fileService.getAll(pageRequest).map(File::toDto);
    }

    @PostMapping
    public FileDto uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.save(file).toDto();
    }

    @GetMapping(path = "/{fileId:[\\d]+}")
    public void getFile(@PathVariable Long fileId, HttpServletResponse response) {
        try {
            Files.copy(fileService.getFilePath(fileId), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}
