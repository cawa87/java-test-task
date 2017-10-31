package com.ronte.api.controller;

import com.ronte.api.entity.File;
import com.ronte.api.entity.FileStream;
import com.ronte.api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final FileService fileService;

    @Autowired
    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public List<File> list() {
        return fileService.getFiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> get(@PathVariable("id") String id) {
        FileStream fileStream = fileService.getFile(id);

        return ResponseEntity
                .ok()
                .contentLength(fileStream.getLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(fileStream.getStream()));
    }

    @PostMapping("/")
    public void add(@RequestParam("file") MultipartFile file) {
        fileService.addFile(file);
    }
}
