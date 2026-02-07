package com.zxs.people.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OllamaOkHttpConfig {

    @Bean
    public OkHttpClient ollamaOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(30))
                .readTimeout(Duration.ofSeconds(180))   // 必须 ≥ 120
                .writeTimeout(Duration.ofSeconds(30))
                .callTimeout(Duration.ofMinutes(3))
                .retryOnConnectionFailure(true)
                .build();
    }
}
