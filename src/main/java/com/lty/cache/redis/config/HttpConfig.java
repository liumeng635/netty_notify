package com.lty.cache.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * http 配置
 * @author zhouyongbo
 */
@Component
public class HttpConfig {


    @Bean
    public RestTemplate get(){
      return new RestTemplate();
    };

}
