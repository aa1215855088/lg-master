package com.lg.product.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 商品编辑 图片上传 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */

@RestController
@RequestMapping( value = "/upload",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - UploadController",tags = "商品录入Api",produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
public class UploadController extends BaseController {
    @Reference
    public UploadService uploadService;

    @PostMapping("/uploadImage")
    @ApiOperation( httpMethod = "POST",value = "上传商品图片")
    public Wrapper upload(@RequestBody @ApiParam("上传商家图片") MultipartFile image){
        logger.info("上传商品图片 {},",image);
        Wrapper<String> stringWrapper = uploadService.uploadImage(image);
        return stringWrapper;

    }





}
