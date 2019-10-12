package com.cloud.producer.config;

import com.cloud.producer.handler.DataSourceRouteHandler;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RoutingDataSourceConfig extends AbstractRoutingDataSource {

    public static final Map<String, List<String>> ROUTE_TYPE_MAP = Maps.newHashMap();

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceRouteHandler.getRouteKey();
    }

    public void setRouteTypeMap(Map<String, String> map) {
        if (MapUtils.isEmpty(map)) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            ROUTE_TYPE_MAP.put(key, Arrays.asList(value.split(",")));
        }
    }
}
