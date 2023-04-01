package com.hyojhand.springbeanscope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("singleton")
    public SingletonBean singletonBean() {
        return new SingletonBean();
    }

    @Bean
    @Scope("prototype")
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }

    @Bean
    @Scope("singleton")
    public ClientBean clientBean() {
        return new ClientBean(prototypeBean());
    }

    @Bean
    @Scope("singleton")
    public ClientProviderBean clientProviderBean() {
        return new ClientProviderBean();
    }
}
