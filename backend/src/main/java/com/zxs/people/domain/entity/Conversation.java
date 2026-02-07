package com.zxs.people.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conversation")

public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "session_id")
    private Long sessionId;
    
    @Column(name = "user_input", columnDefinition = "TEXT")
    private String userInput;
    
    @Column(name = "ai_response", columnDefinition = "TEXT")
    private String aiResponse;
    
    @Column(name = "model_name")
    private String modelName;
    
    @Column(name = "response_time", precision = 10, scale = 2)
    private BigDecimal responseTime;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}