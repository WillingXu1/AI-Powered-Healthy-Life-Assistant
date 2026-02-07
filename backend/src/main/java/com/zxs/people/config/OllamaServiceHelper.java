package com.zxs.people.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OllamaServiceHelper {
    
    private final VectorStore vectorStore;
    private final OllamaConfigProperties config;
    
    /**
     * 带超时的向量检索 - 主方法
     */
    public String retrieveContextWithTimeout(String query) {
        if (!StringUtils.hasText(query)) {
            log.warn("查询内容为空");
            return "";
        }
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            try {
                return retrieveContextInternal(query);
            } catch (Exception e) {
                log.error("向量检索异常", e);
                return "";
            }
        });
        
        try {
            long timeoutSeconds = config.getTimeout().getRetrieval().getSeconds();
            return future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            log.warn("向量检索超时（{}秒），返回空上下文", 
                config.getTimeout().getRetrieval().getSeconds());
            future.cancel(true);
            return "";
        } catch (Exception e) {
            log.error("检索任务异常", e);
            return "";
        } finally {
            executor.shutdownNow();
        }
    }
    
    /**
     * 内部检索逻辑 - Spring AI 1.0.0-M5 的正确用法
     */
    private String retrieveContextInternal(String query) {
        long startTime = System.currentTimeMillis();
        
        try {
            // Spring AI 1.0.0-M5 的最新正确用法
            SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(config.getLimits().getSimilarityResults())
                .build();
            
            List<Document> documents = vectorStore.similaritySearch(searchRequest);
            
            if (documents == null || documents.isEmpty()) {
                log.debug("未检索到相关文档");
                return "";
            }
            
            log.debug("检索到 {} 个相关文档", documents.size());
            return buildContextFromDocuments(documents);
            
        } finally {
            long cost = System.currentTimeMillis() - startTime;
            log.debug("向量检索耗时: {}ms", cost);
        }
    }
    
    /**
     * 从文档构建上下文
     */
    private String buildContextFromDocuments(List<Document> documents) {
        StringBuilder context = new StringBuilder();
        int charCount = 0;
        int maxChars = config.getLimits().getContextChars();
        int processedCount = 0;
        
        for (Document doc : documents) {
            if (doc == null) {
                continue;
            }
            
            String content = doc.getContent();
            if (!StringUtils.hasText(content)) {
                continue;
            }
            
            // 清理内容
            content = content.trim();
            if (content.isEmpty()) {
                continue;
            }
            
            // 检查长度限制
            if (charCount + content.length() > maxChars) {
                int remaining = maxChars - charCount;
                if (remaining > 50) { // 至少保留50字符
                    String truncated = content.substring(0, Math.min(content.length(), remaining));
                    context.append("\n---\n")
                          .append(truncated)
                          .append("...");
                    charCount += truncated.length() + 3; // +3 for "..."
                }
                break;
            }
            
            context.append("\n---\n").append(content);
            charCount += content.length();
            processedCount++;
        }
        
        if (processedCount > 0) {
            log.debug("处理了 {} 个文档，生成上下文 {} 字符", 
                processedCount, charCount);
        }
        
        return context.toString();
    }
    
    /**
     * 构建优化的prompt
     */
    public String buildOptimizedPrompt(String question, String context) {
        if (!StringUtils.hasText(question)) {
            throw new IllegalArgumentException("问题不能为空");
        }
        
        String basePromptTemplate = """
            你是一名专业的人体健康AI医生。请根据以下医学知识回答问题。
            
            相关知识：
            %s
            
            问题：%s
            
            回答要求：
            1. 首先基于上述知识进行回答
            2. 如果知识未涵盖，可以补充通用医学常识
            3. 回答要简洁、专业、准确
            4. 标注关键信息点和注意事项
            5. 如涉及治疗建议，请注明"仅供参考，具体请咨询医生"
            
            专业回答：""";
        
        String contextPart = StringUtils.hasText(context) ? context : "（暂无相关医学知识）";
        String fullPrompt = String.format(basePromptTemplate, contextPart, question);
        
        // 限制prompt总长度
        int maxLength = config.getLimits().getMaxPromptLength();
        if (fullPrompt.length() > maxLength) {
            log.warn("Prompt过长，进行截断: {} > {}", fullPrompt.length(), maxLength);
            fullPrompt = fullPrompt.substring(0, maxLength - 3) + "...";
        }
        
        log.debug("构建的prompt长度: {} 字符", fullPrompt.length());
        return fullPrompt;
    }
    
    /**
     * 获取配置信息（用于调试）
     */
    public String getConfigInfo() {
        return String.format("""
            配置信息：
              检索超时: %d秒
              推理超时: %d秒
              上下文最大字符: %d
              检索结果数: %d
              Prompt最大长度: %d
            """,
            config.getTimeout().getRetrieval().getSeconds(),
            config.getTimeout().getInference().getSeconds(),
            config.getLimits().getContextChars(),
            config.getLimits().getSimilarityResults(),
            config.getLimits().getMaxPromptLength()
        );
    }
}