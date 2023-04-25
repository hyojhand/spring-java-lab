package com.hyojhand.springaop.pointcut;

import com.hyojhand.springaop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinTest() {
//        pointcut.setExpression("within(com.hyojhand.springaop.member.MemberServiceImpl)");
//        pointcut.setExpression("within(com.hyojhand.springaop.member.*Service*)");
        pointcut.setExpression("within(com.hyojhand.springaop..*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("부모 타입을 지정하면 안되고, 정확하게 지정해야 한다")
    void subPackageTest() {
        pointcut.setExpression("within(com.hyojhand.springaop.member.MemberService)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

}
