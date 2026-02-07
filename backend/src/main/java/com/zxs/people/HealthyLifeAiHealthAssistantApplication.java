package com.zxs.people;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: zxs
 * @Date: 2025/2/25
 * @ClassName: PeopleHealthSmartMedicineApplication
 * @Version: 1.0
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.zxs.people.mapper")
public class HealthyLifeAiHealthAssistantApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthyLifeAiHealthAssistantApplication.class, args);
    }
}
