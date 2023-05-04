package com.hyojhand.springaop.retry;

import com.hyojhand.springaop.aop.retry.RetryAspect;
import com.hyojhand.springaop.aop.retry.RetryService;
import com.hyojhand.springaop.aop.trace.TraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({TraceAspect.class, RetryAspect.class})
@SpringBootTest
public class RetryTest {

    @Autowired
    RetryService retryService;

    @Test
    @DisplayName("5번 요청하는 테스트")
    void try_fiveTime_Test() {
        for (int i = 0; i < 5; i++) {
            log.info("client request {}", i + 1);
            retryService.request("data" + i);
        }
    }

}
