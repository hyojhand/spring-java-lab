package com.hyojhand.springbeanscope;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class SingletonBean {

    @PostConstruct
    public void init() {
        log.info("SingletonBean init");
    }

    @PreDestroy
    public void destroy() {
        log.info("SingletonBean destroy");
    }

}
