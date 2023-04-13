package com.hyojhand.springaop.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 포인트 컷 모듈화
 */
public class Pointcuts {

    // order 패키지와 하위 패키지 모두
    @Pointcut("execution(* com.hyojhand.springaop.order..*(..))")
    public void allOrder() {
    }

    // 클래스 이름 패턴이 Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {
    }

    // allOrder + allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {
    }
}
