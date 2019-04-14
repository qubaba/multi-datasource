package com.qubaba.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
//在事物之前执行  要不会获取不到信息
@Order(-10)
@Component
public class DynamicDataSourceAspect {

    //service执行之前动态注入数据源
    @Before("execution( * com.qubaba.service.*Service.*(..) )")
    public void setDataSource(JoinPoint point) {
        //获取当前调用到的方法
        String methodName = point.getSignature().getName();
        if (methodName.startsWith("select") || methodName.startsWith("find")
                || methodName.startsWith("get")) {
            // 设置当前数据库为读数据库
            DataSourceContextHolder.setDB("read");
        } else if (methodName.startsWith("add") || methodName.startsWith("save")
                || methodName.startsWith("create") || methodName.startsWith("update") || methodName.startsWith("edit")
                || methodName.startsWith("del")) {
            // 设置当前数据库为写数据库
            DataSourceContextHolder.setDB("wirte");
        }
    }


    @After("execution( * com.qubaba.service.*Service.*(..) )")
    public void clearDataSource() {
        //service执行完成后清除数据源信息
        DataSourceContextHolder.clearDB();
    }
}
