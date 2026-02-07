package com.zxs.people.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.config.ConversationPersistenceService;
import com.zxs.people.entity.Conversation;
import com.zxs.people.entity.vo.conversation.ConversationAddVo;
import com.zxs.people.entity.vo.conversation.ConversationQueryVo;
import com.zxs.people.entity.vo.conversation.ConversationUpdateVo;
import com.zxs.people.exception.LinyiException;
import com.zxs.people.mapper.ConversationMapper;
import com.zxs.people.service.ConversationService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.zxs.people.common.model.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

/**
 * @Author: zxs
 * @Date: 2025-02-26 13:27:06
 * @ClassName: ConversationServiceImpl
 * @Version: 1.0
 * @Description: 对话 服务实现层
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation>
        implements ConversationService {

    @Autowired
    private ConversationMapper conversationMapper;

//        @Resource
//        private OllamaConfig ollamaConfig;

    @Value("${spring.ai.ollama.chat.options.model}")
    private String defaultChatOptionsModel;

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private ConversationPersistenceService persistenceService;
    @Override
    public PageResult<Conversation> conversationPage(ConversationQueryVo conversationQueryVo) {
        LambdaQueryWrapper<Conversation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Optional.ofNullable(conversationQueryVo.getId()).isPresent(), Conversation::getId,
                conversationQueryVo.getId());
        queryWrapper.eq(Optional.ofNullable(conversationQueryVo.getUserId()).isPresent(),
                Conversation::getUserId,
                conversationQueryVo.getUserId());
        queryWrapper.eq(StringUtils.isNotBlank(conversationQueryVo.getUserInput()), Conversation::getUserInput,
                conversationQueryVo.getUserInput());
        queryWrapper.eq(StringUtils.isNotBlank(conversationQueryVo.getAiResponse()),
                Conversation::getAiResponse,
                conversationQueryVo.getAiResponse());
        queryWrapper.eq(Optional.ofNullable(conversationQueryVo.getAiResponse()).isPresent(),
                Conversation::getAiResponse, conversationQueryVo.getAiResponse());
        queryWrapper.gt(Optional.ofNullable(conversationQueryVo.getStartConversationTime()).isPresent(),
                Conversation::getConversationTime, conversationQueryVo.getStartConversationTime());
        queryWrapper.lt(Optional.ofNullable(conversationQueryVo.getEndConversationTime()).isPresent(),
                Conversation::getConversationTime, conversationQueryVo.getEndConversationTime());
        queryWrapper.eq(StringUtils.isNotBlank(conversationQueryVo.getModelName()), Conversation::getModelName,
                conversationQueryVo.getModelName());
        queryWrapper.ge(Optional.ofNullable(conversationQueryVo.getResponseTime()).isPresent(),
                Conversation::getResponseTime, conversationQueryVo.getResponseTime());

        // 分页数据
        Page<Conversation> page = new Page<>(conversationQueryVo.getPageNum(),
                conversationQueryVo.getPageSize());
        // 查询数据
        Page<Conversation> pageNew = conversationMapper.selectPage(page, queryWrapper);
        // 返回分页数据
        return new PageResult<>(pageNew.getRecords(), pageNew.getTotal(), pageNew.getPages(),
                conversationQueryVo.getPageNum(), conversationQueryVo.getPageSize());
    }

    @Override
    public Boolean conversationAdd(ConversationAddVo conversationAddVo) {
        // 创建实体对象
        Conversation conversation = new Conversation();
        // 复制属性
        BeanUtils.copyProperties(conversationAddVo, conversation);
        // 插入数据
        return conversationMapper.insert(conversation) > 0 ? true : false;
    }

    @Override
    public Boolean conversationUpdate(ConversationUpdateVo conversationUpdateVo) {
        // 根据ID查询数据
        Conversation byId = this.getById(conversationUpdateVo.getId());
        // 判断数据是否存在
        if (Optional.ofNullable(byId).isEmpty()) {
            log.error("数据不存在");
            return false;
        }
        // 复制属性
        BeanUtils.copyProperties(conversationUpdateVo, byId);
        // 修改数据
        return conversationMapper.updateById(byId) > 0 ? true : false;
    }

    @Override
    public List<Conversation> getHistoryNum(Integer num) {
        // 查询数据
        LambdaQueryWrapper<Conversation> queryWrapper = new LambdaQueryWrapper<>();
        // 根据用户ID查询历史记录
        queryWrapper.eq(Conversation::getUserId, StpUtil.getLoginId())
                .orderByDesc(Conversation::getConversationTime).last("limit " + num);
        return conversationMapper.selectList(queryWrapper);
    }

//    @Override
//    public Conversation getOllama(String msg) {
//        return getOllama(msg, null);
//    }

    @Override
    public List<Conversation> listBySessionId(Long sessionId) {
        LambdaQueryWrapper<Conversation> qw = new LambdaQueryWrapper<>();
        qw.eq(Conversation::getUserId, StpUtil.getLoginId())
                .eq(Conversation::getSessionId, sessionId)
                .orderByAsc(Conversation::getId);
        return conversationMapper.selectList(qw);
    }

//        @Override
//        public Conversation getOllama(String msg, Long sessionId) {
//                // 记录接收到的问题消息
//                log.info("问题是:{}", msg);
//                // 初始化结果字符串
//                String result = "";
//
//                // 检查消息是否为空，如果为空则返回错误提示
//                if (StringUtils.isBlank(msg)) {
//                        log.error("请输入chat内容");
//                        throw new LinyiException("请输入chat内容");
//                }
//
//                // 手动检索本地知识库，然后将检索到的上下文与用户问题一并提交给模型
//                long startTime = System.nanoTime();
//                var similar = vectorStore.similaritySearch(msg);
//                StringBuilder context = new StringBuilder();
//                for (var d : similar) {
//                        context.append("\n---\n");
//                        context.append(d.getContent());
//                }
//                String finalPrompt = "你是一名人体健康AI医生。先基于以下本地知识回答，如果知识未涵盖再综合自身知识作答，并标注依据来源要点。" +
//                                "\n本地知识: " + context +
//                                "\n问题: " + msg +
//                                "\n请给出清晰、权威且可执行的回答。";
//                String content = chatClient
//                                .prompt()
//                                .user(finalPrompt)
//                                .options(OllamaOptions.create()
//                                                .withModel(defaultChatOptionsModel)
//                                                .withTemperature(0.4))
//                                .call()
//                                .content();
//                long endTime = System.nanoTime();
//
//                String formattedTimeTaken = String.format("%.2f", (endTime - startTime) / 1e9);
//                result = content;
//                log.info("回答:{}", result);
//
//                // 返回成功结果，包含聊天机器人的回复
//                Conversation conversation = Conversation.builder()
//                                .userId(Integer.valueOf(StpUtil.getLoginId().toString()))
//                                .sessionId(sessionId)
//                                .userInput(msg)
//                                .aiResponse(result)
//                                .modelName(defaultChatOptionsModel)
//                                .responseTime(new BigDecimal(formattedTimeTaken.toString()))
//                                .build();
//                conversationMapper.insert(conversation);
//                return conversation;
//        }



    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Conversation getOllama(String msg, Long sessionId) {

        log.info("问题是:{}", msg);

        if (StringUtils.isBlank(msg)) {
            log.error("请输入chat内容");
            throw new LinyiException("请输入chat内容");
        }

        // ====== 构造 prompt（原样保留） ======
        long startTime = System.nanoTime();
        var similar = vectorStore.similaritySearch(msg);
        StringBuilder context = new StringBuilder();
        for (var d : similar) {
            context.append("\n---\n");
            context.append(d.getContent());
        }

        String finalPrompt =
                "你是一名人体健康AI医生。先基于以下本地知识回答，如果知识未涵盖再综合自身知识作答，并标注依据来源要点。" +
                        "\n本地知识: " + context +
                        "\n问题: " + msg +
                        "\n请给出清晰、权威且可执行的回答。";

        // ====== 调用 Ollama（关键：这里没有事务） ======
        String content = chatClient
                .prompt()
                .user(finalPrompt)
                .options(OllamaOptions.create()
                        .withModel(defaultChatOptionsModel)
                        .withTemperature(0.4))
                .call()
                .content();

        long endTime = System.nanoTime();
        String formattedTimeTaken =
                String.format("%.2f", (endTime - startTime) / 1e9);

        log.info("回答:{}", content);

        // ====== 构造对象（不存库） ======
        Conversation conversation = Conversation.builder()
                .userId(Integer.valueOf(StpUtil.getLoginId().toString()))
                .sessionId(sessionId)
                .userInput(msg)
                .aiResponse(content)
                .modelName(defaultChatOptionsModel)
                .responseTime(new BigDecimal(formattedTimeTaken))
                .build();

        // 存数据库（短事务）
        persistenceService.save(conversation);

        return conversation;
    }

    @Override
    public Conversation getApiLLM(String prompt) {
        return null;
    }
}
