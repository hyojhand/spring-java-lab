package com.hyojhand.springbeanscope;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class PrototypeBean {

    private int count = 0;

    public void addCount() {
        count++;
    }

    public int getCount() {
        return count;
    }

    @PostConstruct
    public void init() {
        log.info("PrototypeBean init " + this);
    }

    @PreDestroy
    public void destroy() {
        log.info("PrototypeBean destroy");
    }
}
