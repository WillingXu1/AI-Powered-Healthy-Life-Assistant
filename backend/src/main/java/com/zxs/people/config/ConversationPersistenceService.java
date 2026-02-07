package com.zxs.people.config;

import com.zxs.people.entity.Conversation;
import com.zxs.people.mapper.ConversationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversationPersistenceService {

    @Transactional
    public void save(Conversation conversation) {
        conversationMapper.insert(conversation);
    }

    @Autowired
    private ConversationMapper conversationMapper;
}
