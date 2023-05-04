package com.hyojhand.springaop.aop.retry;

import com.hyojhand.springaop.aop.trace.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class RetryRepository {

    private static int tryCount = 0;

    /**
     * 5번에 1번 실패하는 요청
     */
    @Trace
    @Retry(value = 4)
    public String save(String itemId) {
        tryCount++;

        if (tryCount % 5 == 0) {
            throw new IllegalArgumentException("예외 발생");
        }

        return "ok";
    }
}
