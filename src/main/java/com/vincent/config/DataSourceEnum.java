package com.vincent.config;

/**
 * 数据源类型枚举类
 */
public enum DataSourceEnum {

    WRITE_DATA_SOURCE(DataSourceEnum.WRITE_DATA_SOURCE_NAME),
    READ_DATA_SOURCE(DataSourceEnum.READ_DATA_SOURCE_NAME);

    public static final String WRITE_DATA_SOURCE_NAME = "writeDataSource";

    public static final String READ_DATA_SOURCE_NAME = "readDataSource";

    public String name;

    DataSourceEnum(String name) {
        this.name = name;
    }
}
