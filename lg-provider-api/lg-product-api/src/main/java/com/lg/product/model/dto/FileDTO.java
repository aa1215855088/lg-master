package com.lg.product.model.dto;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;


public class FileDTO implements Serializable  {


    private static final long serialVersionUID = -72658985477163029L;
    private String name;

    private String originalFilename;


    private  byte[] bytes;

    private InputStream inputStream;

    public FileDTO() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public void setBytes(byte[] bytes){
        this.bytes = bytes;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getName() {
        return name;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
