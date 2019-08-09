package com.cloud.producer.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ProducerProperties.PREFIX)
public class ProducerProperties {

    public static final String PREFIX = "producer";

    private String packagePath;
}
