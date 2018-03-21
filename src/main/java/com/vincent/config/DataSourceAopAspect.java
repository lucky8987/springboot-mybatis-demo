package com.vincent.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * 通过 aop 切换datasource
 */
@Aspect
@Component
public class DataSourceAopAspect implements PriorityOrdered {

    @Before(value = "execution(* com.vincent.dao.UserDao.get*(..))")
    public void setReadDataSource(JoinPoint point) {
        DataSourceContextHolder.setRead();
    }

    @Before(value = "execution(* com.vincent.dao.UserDao.add*(..))")
    public void setWriteDataSource(JoinPoint point) {
        DataSourceContextHolder.setWrite();
    }

    /**
     * 自定义优先级，确保在事务开启前切换数据源，否则事务不生效
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
