package com.tempalych.fcrdle.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({@PropertySource(value = "classpath:application.yml",
        factory = YamlPropertySourceFactory.class)
})
public class AppConfig {
}
