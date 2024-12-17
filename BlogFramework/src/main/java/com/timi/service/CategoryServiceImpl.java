package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.entity.Article;
import com.timi.entity.Category;
import com.timi.mapper.CategoryMapper;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.utils.SysConstant;
import com.timi.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper=new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SysConstant.ARTICLE_STATUS_NORMAL);
        List<Article> articles=articleService.list(articleWrapper);
        //获取文章的分类id，同时去重
        Set<Long> categoryIds=articles.stream()
                .map(o -> o.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        LambdaQueryWrapper<Category> categoryWrapper=new LambdaQueryWrapper<>();
        List<Category> categories=listByIds(categoryIds);
        categories.stream()
                .filter(category -> SysConstant.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装成Vo
        List<CategoryVo> categoryVos= BeanCopyUtils.copyBeanList(categories,CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}
