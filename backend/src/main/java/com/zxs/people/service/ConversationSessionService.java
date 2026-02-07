package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.entity.ConversationSession;

import java.util.List;

public interface ConversationSessionService extends IService<ConversationSession> {
    List<ConversationSession> listMySessions();

    ConversationSession createSession(String title, String modelName);

    boolean closeSession(Long sessionId);
}
