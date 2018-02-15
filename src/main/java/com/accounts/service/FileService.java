package com.accounts.service;

import com.accounts.api.ICrudAPI;
import com.accounts.api.model.FileRec;
import com.accounts.dao.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by pasha on 15.02.18.
 */
@Service
public class FileService implements ICrudAPI<FileRec> {

    @Autowired
    FileRepository fileRepository;


    @Override
    public List<FileRec> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public Optional<FileRec> findById(long id) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public FileRec save(FileRec value) {
        return fileRepository.save(value);
    }
}
