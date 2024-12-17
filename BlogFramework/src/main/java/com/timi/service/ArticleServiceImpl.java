package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.timi.entity.Article;
import com.timi.mapper.ArticleMapper;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.utils.SysConstant;
import com.timi.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SysConstant.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page=new Page(SysConstant.START_PAGE,SysConstant.PAGE_SIZE);
        page(page,queryWrapper);
        List<Article> articles=page.getRecords();
        List<HotArticleVo> articleVos=BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);
//        for (Article article:articles) {
//            HotArticleVo vo= BeanCopyUtils.copyBean(article,HotArticleVo.class);
//            articleVos.add(vo);
//        }

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList() {

        return null;
    }
}
