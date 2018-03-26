package com.test.entities;

import com.test.dtos.file.FileDto;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File extends BaseEntity {

    @Column(name = "file_name")
    private String  fileName;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Account creator;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public FileDto toDto() {
        FileDto fileDto = new FileDto();
        fileDto.setFileId(getId());
        fileDto.setFileName(getFileName());
        Account creator = getCreator();
        if (null != creator) {
            fileDto.setCreator(creator.toDto());
        }

        return fileDto;
    }
}
