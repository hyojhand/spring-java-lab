package com.hyojhand.springaop.pointcut;

import com.hyojhand.springaop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
public class ArgsTest {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    // 포인트 컷 expression 지정 메서드
    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    /**
     * execution(* *(java.io.Serializable)): 메서드의 시그니처로 판단 (정적)
     * args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적)
     */

    @Test
    void argsTest() {
        assertAll(
                () -> assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue(),
                () -> assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue(),
                () -> assertThat(pointcut("args(java.io.Serializable)").matches(helloMethod, MemberServiceImpl.class)).isTrue(),
                () -> assertThat(pointcut("args()").matches(helloMethod, MemberServiceImpl.class)).isFalse(),
                () -> assertThat(pointcut("args(..)").matches(helloMethod, MemberServiceImpl.class)).isTrue(),
                () -> assertThat(pointcut("args(*)").matches(helloMethod, MemberServiceImpl.class)).isTrue(),
                () -> assertThat(pointcut("args(String,..)").matches(helloMethod, MemberServiceImpl.class)).isTrue()
        );
    }

}
