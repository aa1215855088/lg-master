package com.lg.order.aop;

import com.google.common.base.Throwables;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
 * <p>
 * *
 * 服务层异常处理器
 * <p>
 * Created by xuzilou on 2018/5/28.
 */
@Order(100)
@Component
@Aspect
@Slf4j
public class ServiceExceptionHandle {

    @Pointcut(value = "execution(public * com.lg.order.service.impl..*.*(..))")
    private void servicePointcut() {
    }

    /**
     * 任何持有@Transactional注解的方法
     */
    @Pointcut(value = "@annotation(org.springframework.transaction.annotation.Transactional)")
    private void transactionalPointcut() {

    }

    /**
     * 异常处理切面
     * 将异常包装为Response，避免dubbo进行包装
     *
     * @param pjp 处理点
     * @return Object
     */
    @Around("servicePointcut() && !transactionalPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        try {
            return pjp.proceed();
        } catch (BusinessException e) { // 业务自定义异常
            processException(pjp, args, e);
            return WrapMapper.wrap(e.getCode() == 0 ? Wrapper.ERROR_CODE : e.getCode(), e.getMessage());
        } catch (Exception e) {
            processException(pjp, args, e);
            return WrapMapper.error("服务调用失败");
        } catch (Throwable throwable) {
            processException(pjp, args, throwable);
            return WrapMapper.error("系统异常");
        }
    }


    /**
     * 处理异常
     *
     * @param joinPoint 切点
     * @param args      参数
     * @param throwable 异常
     */
    private void processException(final ProceedingJoinPoint joinPoint, final Object[] args, Throwable throwable) {
        String inputParam = "";
        if (args != null && args.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (Object arg : args) {
                sb.append(",");
                sb.append(arg);
            }
            inputParam = sb.toString().substring(1);
        }
        log.warn("\n 方法: {}\n 入参: {} \n 错误信息: {}", joinPoint.toLongString(), inputParam,
                Throwables.getStackTraceAsString(throwable));
    }
}
