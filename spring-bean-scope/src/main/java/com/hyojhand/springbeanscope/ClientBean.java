package com.hyojhand.springbeanscope;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Slf4j
public class ClientBean {

    private final PrototypeBean prototypeBean;

    @Autowired
    public ClientBean(PrototypeBean prototypeBean) {
        this.prototypeBean = prototypeBean;
    }

    public int logic() {
        prototypeBean.addCount();
        return prototypeBean.getCount();
    }

    @PostConstruct
    public void init() {
        log.info("ClientBean init");
    }
}
