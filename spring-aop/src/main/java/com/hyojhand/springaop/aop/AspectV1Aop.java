package com.hyojhand.springaop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 여러 포인트 컷을 가지는 Advice
 * 적용되는 AOP
 * OrderService - doLog, doTransaction
 * OrderRepository - doLog
 */
@Slf4j
@Aspect
public class AspectV1Aop {

    // order 패키지와 하위 패키지 모두
    @Pointcut("execution(* com.hyojhand.springaop.order..*(..))")
    private void allOrder() {
    }

    // 클래스 이름 패턴이 Service
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {
    }

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }

    // order 패키지 하위 + Service 클래스 이름 패턴 일때, 임의의 Transaction AOP 적용
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

}
