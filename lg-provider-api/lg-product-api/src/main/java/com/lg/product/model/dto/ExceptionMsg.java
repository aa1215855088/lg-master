package com.lg.product.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * @program: lg-master
 * @description:
 * @author: 徐子楼
 * @create: 2019-02-22 17:45
 **/
@Data
public class ExceptionMsg implements Serializable {

    private static final long serialVersionUID = -4046172934930118635L;
    /**
     * 时间
     */
    private LocalDateTime createTime;

    /**
     * 方法
     */

    private String exceptionMethod;

    /**
     * 参数
     */
    private String param;


    /**
     * 错误信息
     */
    private String errorMessage;


    /**
     * 处理人邮箱
     */
    private String email;
}
