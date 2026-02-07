package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.Articles;
import com.zxs.people.entity.vo.articles.ArticlesAddVo;
import com.zxs.people.entity.vo.articles.ArticlesQueryVo;
import com.zxs.people.entity.vo.articles.ArticlesUpdateVo;

/**
* @Author: zxs
* @Date: 2025-12-20 08:42:14
* @ClassName: ArticlesService
* @Version: 1.0
* @Description: 文章 服务层
*/
public interface ArticlesService extends IService<Articles> {
    /**
     * 分页查询
     *
     * @param articlesQueryVo 分页查询实体
     * @return PageResult<Articles>
     */
    PageResult<Articles> articlesPage(ArticlesQueryVo articlesQueryVo);

    /**
     * 新增
     *
     * @param articlesAddVo 新增实体
     * @return Boolean
     */
    Boolean articlesAdd(ArticlesAddVo articlesAddVo);

    /**
     * 修改
     *
     * @param articlesUpdateVo 修改实体
     * @return Boolean
     */
    Boolean articlesUpdate(ArticlesUpdateVo articlesUpdateVo);
}
