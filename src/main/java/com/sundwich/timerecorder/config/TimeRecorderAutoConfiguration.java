package com.sundwich.timerecorder.config;

import com.sundwich.timerecorder.aspect.TimeRecorderAspect;
import com.sundwich.timerecorder.properties.TimeRecorderProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author sundwich
 * @date 2025/08/12
 */
@Configuration
@EnableConfigurationProperties(TimeRecorderProperties.class)
@EnableAspectJAutoProxy
public class TimeRecorderAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TimeRecorderAspect timeRecorderAspect(TimeRecorderProperties properties) {
        return new TimeRecorderAspect(properties);
    }
}
