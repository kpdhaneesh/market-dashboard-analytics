package com.valuemagix.dataengine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zerodhatech.kiteconnect.KiteConnect;

@Configuration
public class KiteConfig {
    @Value("${kite.api_key}")
    private String apiKey;

    @Bean
    public KiteConnect kiteConnect() {
        KiteConnect kiteConnect = new KiteConnect(apiKey);
        return kiteConnect;
    }
}
