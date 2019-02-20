package com.lg.product.exceptions;

import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

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
 * @create: 2019-02-19 21:32
 **/
@Slf4j
public class ProductBizException extends BusinessException {
    private static final long serialVersionUID = -6552248511084911254L;

    /**
     * Instantiates a new Uac rpc exception.
     */
    public ProductBizException() {
    }

    /**
     * Instantiates a new Uac rpc exception.
     *
     * @param code      the code
     * @param msgFormat the msg format
     * @param args      the args
     */
    public ProductBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
        log.info("<== MdcRpcException, code:{}, message:{}", this.code, super.getMessage());
    }

    /**
     * Instantiates a new Uac rpc exception.
     *
     * @param code the code
     * @param msg  the msg
     */
    public ProductBizException(int code, String msg) {
        super(code, msg);
        log.info("<== MdcRpcException, code:{}, message:{}", this.code, super.getMessage());
    }

    /**
     * Instantiates a new Mdc rpc exception.
     *
     * @param codeEnum the code enum
     */
    public ProductBizException(ErrorCodeEnum codeEnum) {
        super(codeEnum.code(), codeEnum.msg());
        log.info("<== MdcRpcException, code:{}, message:{}", this.code, super.getMessage());
    }

    /**
     * Instantiates a new Mdc rpc exception.
     *
     * @param codeEnum the code enum
     * @param args     the args
     */
    public ProductBizException(ErrorCodeEnum codeEnum, Object... args) {
        log.info("<== MdcRpcException, code:{}, message:{}", this.code, super.getMessage());
    }
}
