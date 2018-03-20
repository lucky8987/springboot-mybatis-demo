package com.vincent.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {

    private static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    //用Threadlocal 保存 datasource
    private static final ThreadLocal<String> local = ThreadLocal.withInitial(() -> DataSourceEnum.WRITE_DATA_SOURCE_NAME);

    public static void setRead() {
        local.set(DataSourceEnum.READ_DATA_SOURCE_NAME);
        log.info("数据库切换到读库...");
    }

    public static void setWrite() {
        local.set(DataSourceEnum.WRITE_DATA_SOURCE_NAME);
        log.info("数据库切换到写库...");
    }

    public static String getReadOrWrite() {
        return local.get();
    }
}
