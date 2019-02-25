package com.lg.product.aop;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.dto.ExceptionMsg;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

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

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${project.leader.email}")
    private String projectLeaderEmail;

    @Pointcut(value = "execution(public * com.lg.product.service.impl..*.*(..))")
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
        } catch (BusinessException | ConstraintViolationException e) { // 业务自定义异常
            processException(pjp, args, e,false);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            processException(pjp, args, e,true);
            return WrapMapper.error("服务调用失败");
        } catch (Throwable throwable) {
            processException(pjp, args, throwable,true);
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
    private void processException(final ProceedingJoinPoint joinPoint, final Object[] args, Throwable throwable,
                                  boolean isHandle) {
        String inputParam = "";
        if (args != null && args.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (Object arg : args) {
                sb.append(",");
                sb.append(arg);
            }
            inputParam = sb.toString().substring(1);
        }
        String errorMessage = Throwables.getStackTraceAsString(throwable);
        String method = joinPoint.toLongString();
        if(isHandle){
            ExceptionMsg exceptionMsg = new ExceptionMsg();
            exceptionMsg.setCreateTime(LocalDateTime.now());
            exceptionMsg.setExceptionMethod(method);
            exceptionMsg.setParam(inputParam);
            exceptionMsg.setErrorMessage(errorMessage);
            exceptionMsg.setEmail(projectLeaderEmail);
            this.amqpTemplate.convertAndSend("exceptionMsg", JSON.toJSON(exceptionMsg).toString());
        }
        log.warn("\n 方法: {}\n 入参: {} \n 错误信息: {}", method, inputParam,
                errorMessage);
    }
}
