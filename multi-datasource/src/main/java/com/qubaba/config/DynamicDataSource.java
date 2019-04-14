package com.qubaba.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("当前数据源为:" + DataSourceContextHolder.getDB());
        // 返回当前数据源信息
        return DataSourceContextHolder.getDB();
    }
}
