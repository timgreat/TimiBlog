package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.entity.Article;
import com.timi.entity.Category;
import com.timi.mapper.ArticleMapper;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.utils.SysConstant;
import com.timi.vo.ArticleDetailVo;
import com.timi.vo.ArticleListVo;
import com.timi.vo.HotArticleVo;
import com.timi.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if(categoryId!=null&&categoryId>0){
            articleListVos.stream()
                    .map(new Function<ArticleListVo, ArticleListVo>() {
                        @Override
                        public ArticleListVo apply(ArticleListVo articleListVo) {
                            Category category=categoryService.getById(categoryId);
                            articleListVo.setCategoryName(category.getName());
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
        //封装
        ArticleDetailVo articleDetailVo=BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //查询分类名
        Category category=categoryService.getById(article.getCategoryId());
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }
}
