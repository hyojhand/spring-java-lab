package com.hyojhand.springbeanscope;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SingletonBeanTest {

    @Test
    @DisplayName("싱글톤 빈 같음 테스트")
    void singletonBean_Same_Test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        SingletonBean singletonBean = applicationContext.getBean(SingletonBean.class);
        SingletonBean otherSingletonBean = applicationContext.getBean(SingletonBean.class);
        log.info("singletonBean = " + singletonBean);
        log.info("otherSingletonBean = " + otherSingletonBean);
        assertThat(singletonBean).isSameAs(otherSingletonBean);

        applicationContext.close();
    }
}