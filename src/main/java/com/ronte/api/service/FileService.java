package com.ronte.api.service;

import com.mongodb.gridfs.GridFSDBFile;
import com.ronte.api.entity.File;
import com.ronte.api.entity.FileStream;
import com.ronte.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public List<File> getFiles() {
        List<GridFSDBFile> files = gridFsTemplate.find(null);

        return files.stream()
                .map(x -> new File(x.getId().toString(), x.getFilename(), x.getLength()))
                .collect(Collectors.toList());
    }

    public FileStream getFile(String id) {
        Query criteria = new Query().addCriteria(Criteria.where("_id").is(id));
        GridFSDBFile file = gridFsTemplate.findOne(criteria);
        if (file == null) {
            throw new ResourceNotFoundException();
        }

        return new FileStream(file.getLength(), file.getInputStream());
    }

    public void addFile(MultipartFile file) {
        try {
            gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
