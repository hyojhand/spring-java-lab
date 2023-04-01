package com.hyojhand.springbeanscope;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PrototypeBeanTest {

    @Test
    @DisplayName("프로토타입 빈 다름 테스트")
    void prototypeBean_NotSame_Test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        log.info("find PrototypeBean");
        PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
        log.info("find otherPrototypeBean");
        PrototypeBean otherPrototypeBean = applicationContext.getBean(PrototypeBean.class);
        log.info("prototypeBean = " + prototypeBean);
        log.info("otherPrototypeBean = " + otherPrototypeBean);

        assertThat(prototypeBean).isNotSameAs(otherPrototypeBean);

        applicationContext.close();
    }

    @Test
    @DisplayName("프로토타입 빈의 카운트 테스트")
    void prototypeBean_AddCount_Test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
        prototypeBean.addCount();
        assertThat(prototypeBean.getCount()).isEqualTo(1);

        PrototypeBean otherPrototypeBean = applicationContext.getBean(PrototypeBean.class);
        otherPrototypeBean.addCount();
        assertThat(prototypeBean.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("싱글톤 빈에서 프로토타입 빈을 사용하는 테스트")
    void prototypeBean_WithSingletonBean_Test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        ClientBean clientBean = applicationContext.getBean(ClientBean.class);
        int count = clientBean.logic();
        assertThat(count).isEqualTo(1);

        ClientBean otherClientBean = applicationContext.getBean(ClientBean.class);
        int otherCount = otherClientBean.logic();
        assertThat(otherCount).isEqualTo(2);
    }

    @Test
    @DisplayName("싱글톤 빈에서 provider로 프로토타입 빈을 사용하는 테스트")
    void prototypeBean_Provider_Test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        ClientProviderBean clientBean = applicationContext.getBean(ClientProviderBean.class);
        int count = clientBean.logic();
        assertThat(count).isEqualTo(1);

        ClientProviderBean otherClientBean = applicationContext.getBean(ClientProviderBean.class);
        int otherCount = otherClientBean.logic();
        assertThat(otherCount).isEqualTo(1);
    }

}