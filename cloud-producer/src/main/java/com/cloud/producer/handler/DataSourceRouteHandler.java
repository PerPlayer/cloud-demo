package com.cloud.producer.handler;

public class DataSourceRouteHandler {

    private static final ThreadLocal<String> routeKey = new ThreadLocal<>();

    public static String getRouteKey() {
        return routeKey.get();
    }

    public static void setRouteKey(String key) {
        routeKey.set(key);
    }
}
