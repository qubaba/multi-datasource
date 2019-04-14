package com.qubaba.config;

public class DataSourceContextHolder {

    private DataSourceContextHolder() {
    }

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据库 参数为DataSourceConfig 里Bean的name
     */
    public static void setDB(String type) {
        contextHolder.set(type);
    }

    /**
     * 获取当前数据源
     */
    public static String getDB() {
        return contextHolder.get();
    }

    /**
     * 清空数据源信息
     */
    public static void clearDB() {
        contextHolder.remove();
    }


}
