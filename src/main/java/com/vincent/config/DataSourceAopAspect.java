package com.vincent.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 *
 * <a href = "https://my.oschina.net/guangshan/blog/1808373">在Spring中使用MyBatis的Mapper接口自动生成时，用一个自定义的注解标记在Mapper接口的方法中，<br/>
 * 再利用@Aspect定义一个切面，拦截这个mapper中的增删改查注解实现数据源切换。<br/>
 * 在Spring Boot 1.X(Spring Framework 4.x)中，并不能生效，<br/>
 * 而在Spring Boot 2.X(Spring Framework 5.X)中却能生效。</a><br/>
 *
 * 通过 aop 切换 datasource
 */
@Aspect
@Component
public class DataSourceAopAspect implements PriorityOrdered {

    /**
     * {@link org.apache.ibatis.annotations.Select} 切入点
     */
    private static final String SELECT_POINTCUT = "@annotation(org.apache.ibatis.annotations.Select)";

    /**
     * {@link org.apache.ibatis.annotations.SelectProvider} 切入点
     */
    private static final String SELECTPROVIDER_POINTCUT = "@annotation(org.apache.ibatis.annotations.SelectProvider)";

    /**
     * {@link org.apache.ibatis.annotations.Insert} 切入点
     */
    private static final String INSERT_POINTCUT = "@annotation(org.apache.ibatis.annotations.Insert)";

    /**
     * {@link org.apache.ibatis.annotations.InsertProvider} 切入点
     */
    private static final String INSERTPROVIDER_POINTCUT = "@annotation(org.apache.ibatis.annotations.InsertProvider)";

    /**
     * {@link org.apache.ibatis.annotations.Update} 切入点
     */
    private static final String UPDATE_POINTCUT = "@annotation(org.apache.ibatis.annotations.Update)";

    /**
     * {@link org.apache.ibatis.annotations.UpdateProvider} 切入点
     */
    private static final String UPDATEPROVIDER_POINTCUT = "@annotation(org.apache.ibatis.annotations.UpdateProvider)";

    /**
     * {@link org.apache.ibatis.annotations.Delete} 切入点
     */
    private static final String DELETE_POINTCUT = "@annotation(org.apache.ibatis.annotations.Delete)";

    /**
     * {@link org.apache.ibatis.annotations.DeleteProvider} 切入点
     */
    private static final String DeletePROVIDER_POINTCUT = "@annotation(org.apache.ibatis.annotations.DeleteProvider)";

    /**
     * 切换到读库
     * @param point
     */
    @Before(value = SELECT_POINTCUT + " || " + SELECTPROVIDER_POINTCUT)
    public void setReadDataSource(JoinPoint point) {
        DataSourceContextHolder.setRead();
    }

    /**
     * 切换到写库
     * @param point
     */
    @Before(value = INSERT_POINTCUT + " || " + INSERTPROVIDER_POINTCUT + " || " + UPDATE_POINTCUT + " || " + UPDATEPROVIDER_POINTCUT
            + " || " + DELETE_POINTCUT + " || " + DeletePROVIDER_POINTCUT)
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
