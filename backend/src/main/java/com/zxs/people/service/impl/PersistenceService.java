package com.zxs.people.service.impl;

import com.zxs.people.entity.Conversation;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PersistenceService {

    @Transactional
    public void save(Conversation conversation) {
        try {
            // TODO: 实现实际的保存逻辑
            log.info("保存对话记录: {}", conversation);
        } catch (Exception e) {
            log.error("保存对话记录失败", e);
            throw e;
        }
    }
}