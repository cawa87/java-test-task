package com.test.dtos.file;

import com.test.dtos.account.AccountDto;

public class FileDto {
    private Long fileId;

    private String fileName;

    private AccountDto creator;


    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public AccountDto getCreator() {
        return creator;
    }

    public void setCreator(AccountDto creator) {
        this.creator = creator;
    }
}
