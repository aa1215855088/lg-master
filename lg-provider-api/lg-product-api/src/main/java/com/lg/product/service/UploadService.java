package com.lg.product.service;


import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.dto.FileDTO;

public interface UploadService  {

    Wrapper uploadImage(FileDTO file);
}
