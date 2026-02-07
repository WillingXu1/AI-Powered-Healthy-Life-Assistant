package com.zxs.people.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.NewsArticles;
import com.zxs.people.entity.vo.newsArticles.NewsArticlesAddVo;
import com.zxs.people.entity.vo.newsArticles.NewsArticlesQueryVo;
import com.zxs.people.entity.vo.newsArticles.NewsArticlesUpdateVo;
import com.zxs.people.mapper.NewsArticlesMapper;
import com.zxs.people.service.NewsArticlesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author: zxs
 * @Date: 2025-12-20 08:42:14
 * @ClassName: NewsArticlesServiceImpl
 * @Version: 1.0
 * @Description: 新闻资讯 服务实现层
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@SuppressWarnings({"unchecked", "rawtypes"})
public class NewsArticlesServiceImpl extends ServiceImpl<NewsArticlesMapper, NewsArticles> implements NewsArticlesService {

    @Autowired
    private NewsArticlesMapper newsArticlesMapper;

    @Override
    public PageResult<NewsArticles> newsArticlesPage(NewsArticlesQueryVo newsArticlesQueryVo) {
        LambdaQueryWrapper<NewsArticles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(newsArticlesQueryVo.getUrl()), NewsArticles::getUrl, newsArticlesQueryVo.getUrl());
        queryWrapper.like(StringUtils.isNotBlank(newsArticlesQueryVo.getTitle()), NewsArticles::getTitle, newsArticlesQueryVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(newsArticlesQueryVo.getContent()), NewsArticles::getContent, newsArticlesQueryVo.getContent());
        queryWrapper.like(StringUtils.isNotBlank(newsArticlesQueryVo.getAuthor()), NewsArticles::getAuthor, newsArticlesQueryVo.getAuthor());

        // 优化时间范围查询
        if (newsArticlesQueryVo.getStartPublishTime() != null) {
            queryWrapper.ge(NewsArticles::getPublishTime, newsArticlesQueryVo.getStartPublishTime());
        }
        if (newsArticlesQueryVo.getEndPublishTime() != null) {
            queryWrapper.le(NewsArticles::getPublishTime, newsArticlesQueryVo.getEndPublishTime());
        }

        queryWrapper.like(StringUtils.isNotBlank(newsArticlesQueryVo.getSource()), NewsArticles::getSource, newsArticlesQueryVo.getSource());
        queryWrapper.like(StringUtils.isNotBlank(newsArticlesQueryVo.getSummary()), NewsArticles::getSummary, newsArticlesQueryVo.getSummary());

        // 分页数据
        Page<NewsArticles> page = new Page<>(newsArticlesQueryVo.getPageNum(), newsArticlesQueryVo.getPageSize());

        // 查询数据
        Page<NewsArticles> pageNew = newsArticlesMapper.selectPage(page, queryWrapper);

        // 修复：明确指定泛型类型
        return new PageResult<NewsArticles>(
                pageNew.getRecords(),
                pageNew.getTotal(),
                (int) pageNew.getPages(),
                (int) newsArticlesQueryVo.getPageNum(),
                (int) newsArticlesQueryVo.getPageSize()
        );
    }

    @Override
    public Boolean newsArticlesAdd(NewsArticlesAddVo newsArticlesAddVo) {
        //创建实体对象
        NewsArticles newsArticles = new NewsArticles();
        //复制属性
        BeanUtils.copyProperties(newsArticlesAddVo, newsArticles);
        //插入数据
        return newsArticlesMapper.insert(newsArticles) > 0 ? true : false;
    }

    @Override
    public Boolean newsArticlesUpdate(NewsArticlesUpdateVo newsArticlesUpdateVo) {
        //根据ID查询数据
        NewsArticles byId = this.getById(newsArticlesUpdateVo.getId());
        //判断数据是否存在
        if (Optional.ofNullable(byId).isEmpty()) {
            log.error("数据不存在");
            return false;
        }
        //复制属性
        BeanUtils.copyProperties(newsArticlesUpdateVo, byId);
        //修改数据
        return newsArticlesMapper.updateById(byId) > 0 ? true : false;
    }
}
