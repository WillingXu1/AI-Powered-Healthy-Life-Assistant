package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.NewsArticles;
import com.zxs.people.entity.vo.newsArticles.NewsArticlesAddVo;
import com.zxs.people.entity.vo.newsArticles.NewsArticlesQueryVo;
import com.zxs.people.entity.vo.newsArticles.NewsArticlesUpdateVo;

/**
* @Author: zxs
* @Date: 2025-12-20 08:42:14
* @ClassName: NewsArticlesService
* @Version: 1.0
* @Description: 新闻资讯 服务层
*/
public interface NewsArticlesService extends IService<NewsArticles> {
    /**
     * 分页查询
     *
     * @param newsArticlesQueryVo 分页查询实体
     * @return PageResult<NewsArticles>
     */
    PageResult<NewsArticles> newsArticlesPage(NewsArticlesQueryVo newsArticlesQueryVo);

    /**
     * 新增
     *
     * @param newsArticlesAddVo 新增实体
     * @return Boolean
     */
    Boolean newsArticlesAdd(NewsArticlesAddVo newsArticlesAddVo);

    /**
     * 修改
     *
     * @param newsArticlesUpdateVo 修改实体
     * @return Boolean
     */
    Boolean newsArticlesUpdate(NewsArticlesUpdateVo newsArticlesUpdateVo);
}
