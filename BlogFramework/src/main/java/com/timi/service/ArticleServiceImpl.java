package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.dto.AddArticleDto;
import com.timi.entity.Article;
import com.timi.entity.Category;
import com.timi.exception.SystemException;
import com.timi.mapper.ArticleMapper;
import com.timi.utils.*;
import com.timi.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
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
        articles.stream()
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) {
                        //从redis中获取viewCount值
                        Integer viewConunt=redisCache.getCacheMapValue(SysConstant.REDIS_KEY_OF_VIEWCOUNT,article.getId().toString());
                        article.setViewCount(viewConunt.longValue());
                        return article;
                    }
                }).collect(Collectors.toList());
        List<HotArticleVo> articleVos=BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);
//        for (Article article:articles) {
//            HotArticleVo vo= BeanCopyUtils.copyBean(article,HotArticleVo.class);
//            articleVos.add(vo);
//        }

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //如果有categoryId需要查询时与传入的相同
        queryWrapper.eq(categoryId!=null&&categoryId>0,Article::getCategoryId,categoryId);
        //已发布的文章
        queryWrapper.eq(Article::getStatus,SysConstant.ARTICLE_STATUS_NORMAL);
        //对isTop降序
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
//        for (Article article:page.getRecords()) {
//            Category category=categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //封装
        List<ArticleListVo> articleListVos=BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        if(categoryId!=null&&categoryId>=0){
            articleListVos.stream()
                    .map(new Function<ArticleListVo, ArticleListVo>() {
                        @Override
                        public ArticleListVo apply(ArticleListVo articleListVo) {
                            Category category=categoryService.getById(articleListVo.getCategoryId());
                            if(category!=null){
                                articleListVo.setCategoryName(category.getName());
                            }

                            //从redis中获取viewCount值
                            Integer viewConunt=redisCache.getCacheMapValue(SysConstant.REDIS_KEY_OF_VIEWCOUNT,articleListVo.getId().toString());
                            articleListVo.setViewCount(viewConunt.longValue());

                            return articleListVo;
                        }
                    })
                    .collect(Collectors.toList());
        }

        PageVo pageVo=new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //查询文章
        Article article=this.getById(id);
        //从redis中获取viewCount值
        Integer viewConunt=redisCache.getCacheMapValue(SysConstant.REDIS_KEY_OF_VIEWCOUNT,id.toString());
        article.setViewCount(viewConunt.longValue());
        //封装
        ArticleDetailVo articleDetailVo=BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //查询分类名
        Category category=categoryService.getById(article.getCategoryId());
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中的浏览量
        redisCache.incrementCacheMapValue(SysConstant.REDIS_KEY_OF_VIEWCOUNT,id.toString(),1);
        return ResponseResult.okResult();
    }
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    @Transactional
    public ResponseResult add(AddArticleDto article) {
        //添加博客文章
        if(!StringUtils.hasText(article.getTitle())){
            throw new SystemException(AppHttpCodeEnum.ARTICLE_TITILE_NO_NULL);
        }
        Article art=BeanCopyUtils.copyBean(article,Article.class);
        save(art);
        //添加博客与标签的关联
        articleMapper.addArticleTag(art.getId(),article.getTags());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(title),Article::getTitle,title);
        queryWrapper.like(StringUtils.hasText(summary),Article::getSummary,summary);
        Page<Article> pages=new Page<>(pageNum,pageSize);
        page(pages,queryWrapper);
        PageVo pageVo=new PageVo(pages.getRecords(),pages.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getInfo(Long id) {
        Article article=getById(id);
        ArticleQuaryVo articleQuaryVo=BeanCopyUtils.copyBean(article,ArticleQuaryVo.class);
        articleQuaryVo.setTags(articleMapper.selectArticleTag(id));
        return ResponseResult.okResult(articleQuaryVo);
    }

    @Override
    @Transactional
    public ResponseResult update(ArticleQuaryVo articleQuaryVo) {
        //update article
        Article article=BeanCopyUtils.copyBean(articleQuaryVo,Article.class);
        articleMapper.updateById(article);
        //update article_tag
        articleMapper.deleteArticleTag(articleQuaryVo.getId());
        if(!articleQuaryVo.getTags().isEmpty()){
            articleMapper.addArticleTag(articleQuaryVo.getId(),articleQuaryVo.getTags());
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

}
