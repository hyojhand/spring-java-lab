package com.hyojhand.springaop;

import com.hyojhand.springaop.aop.AspectV4Advice;
import com.hyojhand.springaop.order.OrderRepository;
import com.hyojhand.springaop.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Import(AspectV4Advice.class)
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("AOP 적용 여부 확인 테스트")
    void isAopProxy_Test() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    @DisplayName("orderItem 성공 테스트")
    void orderItem_Success_Test() {
        orderService.orderItem("itemA");
    }

    @Test
    @DisplayName("orderItem 예외 테스트")
    void orderItem_Exception_Test() {
        assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("예외 발생");
    }
}