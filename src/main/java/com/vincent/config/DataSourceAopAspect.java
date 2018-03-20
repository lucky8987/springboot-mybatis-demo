package com.vincent.config;

import org.apache.ibatis.annotations.Select;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 通过 aop 切换datasource
 */
@Aspect
@Component
public class DataSourceAopAspect {

    @Before(value = "@annotation(select)")
    public void setReadDataSource(Select select) {
        DataSourceContextHolder.setRead();
    }

    /*@Before(value = "@annotation(insert) || @annotation(update) || @annotation(delete)")
    public void setReadDataSource(Insert insert, Update update, Delete delete) {
        DataSourceContextHolder.setWrite();
    }*/
}
