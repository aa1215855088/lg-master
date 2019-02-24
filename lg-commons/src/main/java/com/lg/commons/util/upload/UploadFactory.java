package com.lg.commons.util.upload;

import com.qiniu.util.Auth;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @program: demo
 * @description:
 * @author: 徐子楼
 * @create: 2019-02-15 14:55
 **/
public class UploadFactory {
    public static UploadUtil createUpload(String accessKey, String secretKeySpec, String bucketHostName, String bucketName) {
        Auth auth = Auth.create(accessKey, secretKeySpec);
        return new QiniuUtil(bucketHostName, bucketName, auth);
    }
}
