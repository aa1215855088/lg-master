package com.lg.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.commons.util.wrapper.Wrapper;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService  {

    Wrapper uploadImage(MultipartFile image);
}
