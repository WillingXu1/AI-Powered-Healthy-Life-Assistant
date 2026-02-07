package com.zxs.people.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "app.ollama")
@Data
public class OllamaConfigProperties {
    
    private Timeout timeout = new Timeout();
    private Limits limits = new Limits();
    private Fallback fallback = new Fallback();
    
    @Data
    public static class Timeout {
        private Duration retrieval = Duration.ofSeconds(5);
        private Duration inference = Duration.ofSeconds(30);
    }
    
    @Data
    public static class Limits {
        private int contextChars = 800;
        private int similarityResults = 3;
        private int maxPromptLength = 1500;
    }
    
    @Data
    public static class Fallback {
        private boolean enabled = true;
        private String message = "系统处理中，请稍后查看结果";
    }
}