package com.hyojhand.springaop.pointcut;

import com.hyojhand.springaop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        // public java.lang.String com.hyojhand.springaop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    // 가장 정확한 매치 - 접근제어자 반환타입 선언타입.메서드명(파라미터)
    @Test
    void exactMatch() {
        pointcut.setExpression("execution(public String com.hyojhand.springaop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 생략된 버전 - 반환타입(*) 메서드이름(*)(파라미터(..))
    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 메서드명으로 매치 - hello 메서드
    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 메서드명 패턴 매치 - *ell*
    @Test
    void namePatternMatch() {
        pointcut.setExpression("execution(* *ell*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 포인트컷 매치 실패
    @Test
    void matchFalse() {
        pointcut.setExpression("execution(* any(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 패키지명 매치
    @Test
    void packageExactMatch() {
        pointcut.setExpression("execution(* com.hyojhand.springaop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 잘못된 패키지 매치 - .은 해당 위치의 패키지만 포함
    @Test
    void packageMatchFalse() {
        pointcut.setExpression("execution(* com.hyojhand.springaop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 서브 패키지까지 매치 (패키지 ..)
    @Test
    void subPackageMatch() {
        pointcut.setExpression("execution(* com.hyojhand.springaop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 타입 매치 (부모 타입(MemberService) 허용)
    @Test
    void typeMatch() {
        pointcut.setExpression("execution(* com.hyojhand.springaop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 잘못된 타입 매치 - 부모타입으로 지정할때는, 부모 타입에 선언한 메서드가 자식 타입에 있어야만 매치된다.
    @Test
    void notSuperTypeMethodMatch() throws NoSuchMethodException {
        pointcut.setExpression("execution(* com.hyojhand.springaop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    // 파라미터 매치
    @Test
    void argsMatch() {
//        pointcut.setExpression("execution(* *(String))");
//        pointcut.setExpression("execution(* *())"); // 파라미터가 없는 매치
//        pointcut.setExpression("execution(* *(*))"); // 정확히 하나의 파라미터, 모든 타입 허용
//        pointcut.setExpression("execution(* *(..))"); // 숫자와 무관하게 모든 파라미터, 타입 허용 (파라미터가 없어도 된다)
        pointcut.setExpression("execution(* *(String, ..))"); // String으로 시작하고, 숫자와 무관하게 모든 파라미터, 타입 허용 (파라미터가 없어도 된다)
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


}
