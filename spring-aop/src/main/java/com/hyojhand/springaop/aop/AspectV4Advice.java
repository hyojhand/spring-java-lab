package com.hyojhand.springaop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV4Advice {

    @Before("com.hyojhand.springaop.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinpoint) {
        log.info("[before] {}", joinpoint.getSignature());
    }

    @AfterReturning(value = "com.hyojhand.springaop.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "com.hyojhand.springaop.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex);
    }

    @After(value = "com.hyojhand.springaop.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
