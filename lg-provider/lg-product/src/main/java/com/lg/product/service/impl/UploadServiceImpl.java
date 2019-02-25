package com.lg.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.upload.UploadFactory;
import com.lg.commons.util.upload.UploadUtil;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class UploadServiceImpl implements UploadService {

    //引入第一步的七牛配置
    @Value("${qiniu.access.key}")
    private String accesskey;

    @Value("${qiniu.secret.key}")
    private String secretKey;

    @Value("${qiniu.bucket.name}")
    private String bucketName;

    @Value("${qiniu.bucket.host.name}")
    private String bucketHostName;

    @Override
    public Wrapper uploadImage(MultipartFile image) throws BusinessException {
        UploadUtil uploadUtil = UploadFactory.createUpload(this.accesskey, this.secretKey, this.bucketHostName,
                this.bucketName);
        String uploadFile = uploadUtil.uploadFile("/filePath/", image);
        return WrapMapper.ok(uploadFile);
    }
}
