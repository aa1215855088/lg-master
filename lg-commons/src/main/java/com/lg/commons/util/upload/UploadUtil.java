package com.lg.commons.util.upload;

import com.lg.commons.base.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
 * @create: 2019-02-15 14:52
 **/
public interface UploadUtil {
    String uploadFile(MultipartFile multipartFile) throws BusinessException;

    String uploadFile(String filePath, MultipartFile multipartFile) throws BusinessException;

    String uploadFile(MultipartFile multipartFile, String fileName) throws BusinessException;

    String uploadFile(MultipartFile multipartFile, String fileName, String filePath) throws BusinessException;

    String uploadFile(File file) throws BusinessException;

    String uploadFile(String filePath, File file) throws BusinessException;

    String uploadFile(File file, String fileName) throws BusinessException;

    String uploadFile(File file, String fileName, String filePath) throws BusinessException;

    String uploadFile(byte[] data) throws BusinessException;

    String uploadFile(String filePath, byte[] data) throws BusinessException;

    String uploadFile(byte[] data, String fileName) throws BusinessException;

    String uploadFile(byte[] data, String fileName, String filePath) throws BusinessException;
}
