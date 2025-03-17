package com.woopaca.likeknu.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private volatile static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static <T> Optional<T> getBean(Class<T> beanClass) {
        if (ApplicationContextHolder.applicationContext == null) {
            return Optional.empty();
        }
        return Optional.of(applicationContext.getBean(beanClass));
    }
}
