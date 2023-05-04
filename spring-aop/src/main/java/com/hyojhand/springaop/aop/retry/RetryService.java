package com.hyojhand.springaop.aop.retry;

import com.hyojhand.springaop.aop.trace.Trace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetryService {

    private final RetryRepository retryRepository;

    @Trace
    public void request(String itemId) {
        retryRepository.save(itemId);
    }

}
