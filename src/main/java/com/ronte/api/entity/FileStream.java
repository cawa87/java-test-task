package com.ronte.api.entity;

import java.io.InputStream;

public class FileStream {

    private long length;
    private InputStream stream;

    public FileStream(long length, InputStream stream) {
        this.length = length;
        this.stream = stream;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }
}
