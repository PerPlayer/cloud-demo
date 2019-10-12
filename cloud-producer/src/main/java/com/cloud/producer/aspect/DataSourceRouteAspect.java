package com.cloud.producer.aspect;

import com.cloud.producer.config.RoutingDataSourceConfig;
import com.cloud.producer.handler.DataSourceRouteHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class DataSourceRouteAspect {

    Map<String, List<String>> routeTypeMap = RoutingDataSourceConfig.ROUTE_TYPE_MAP;

    @Pointcut("execution(* com.cloud.producer.codegenerator.GenerateController.*(..))")
    private void pointcut(){}

    @Before("pointcut()")
    private void before(JoinPoint point){
        String methodName = point.getSignature().getName();
        for (Map.Entry<String, List<String>> entry : routeTypeMap.entrySet()) {
            List<String> methodPrefixs = entry.getValue();
            for (String methodPrefix : methodPrefixs) {
                if (methodName.startsWith(methodPrefix.trim())) {
                    String key = entry.getKey();
                    DataSourceRouteHandler.setRouteKey(key);
                    System.out.println("Data source switch to " + key);
                    return;
                }
            }
        }
    }
}
