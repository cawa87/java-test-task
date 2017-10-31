package com.ronte.api.entity;

public class File {

    private String id;
    private String name;
    private long length;

    public File(String id, String name, long length) {
        this.id = id;
        this.name = name;
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
