package com.zxs.people.entity.vo.newsArticles;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zxs.people.common.model.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @Author: zxs
* @Date: 2025-12-20 08:42:14
* @ClassName: NewsArticlesQueryVo
* @Version: 1.0
* @Description: 新闻资讯查询实体
*/

@Data
@Schema(name = "新闻资讯查询实体")
public class NewsArticlesQueryVo extends PageResponse implements Serializable {

    /**
     * 转载url
     */
    @TableField("url")
    @Schema(name = "url",description = "转载url",type = "varchar")
    private String url;

    /**
     * 新闻标题
     */
    @TableField("title")
    @Schema(name = "title",description = "新闻标题",type = "varchar")
    private String title;

    /**
     * 新闻内容
     */
    @TableField("content")
    @Schema(name = "content",description = "新闻内容",type = "text")
    private String content;

    /**
     * 作者
     */
    @TableField("author")
    @Schema(name = "author",description = "作者",type = "varchar")
    private String author;

    /**
     * 发布时间，默认为当前时间-开始
     */
    @Schema(name = "startPublishTime",description = "发布时间，默认为当前时间",type = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startPublishTime;
    /**
     * 发布时间，默认为当前时间-结束
     */
    @Schema(name = "endPublishTime",description = "发布时间，默认为当前时间",type = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endPublishTime;

    /**
     * 新闻来源
     */
    @TableField("source")
    @Schema(name = "source",description = "新闻来源",type = "varchar")
    private String source;

    /**
     * 新闻摘要
     */
    @TableField("summary")
    @Schema(name = "summary",description = "新闻摘要",type = "text")
    private String summary;

}
