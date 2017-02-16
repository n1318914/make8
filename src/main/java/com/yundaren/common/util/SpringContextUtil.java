package com.yundaren.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static  <T> T getBean(String name){
        return (T)applicationContext.getBean(name);
    }

    public static  <T> T getBean(Class<T> type){
        return applicationContext.getBean(type);
    }
}