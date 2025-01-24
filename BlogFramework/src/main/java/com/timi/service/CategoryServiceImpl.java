package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.entity.Article;
import com.timi.entity.Category;
import com.timi.mapper.CategoryMapper;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.utils.SysConstant;
import com.timi.utils.WebUtils;
import com.timi.vo.CategoryVo;
import com.timi.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        List<Category> categories=listByIds(categoryIds);
        categories=categories.stream()
                .filter(category -> SysConstant.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装成Vo
        List<CategoryVo> categoryVos= BeanCopyUtils.copyBeanList(categories,CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SysConstant.STATUS_NORMAL);
        List<Category> categories=list(queryWrapper);
        List<CategoryVo> categoryVos=BeanCopyUtils.copyBeanList(categories,CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name),Category::getName,name);
        queryWrapper.eq(StringUtils.hasText(status),Category::getStatus,status);
        Page<Category> pages=new Page<>(pageNum,pageSize);
        page(pages,queryWrapper);
        List<CategoryVo> categoryVos=BeanCopyUtils.copyBeanList(pages.getRecords(), CategoryVo.class);
        PageVo pageVo=new PageVo(categoryVos,pages.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryVo categoryVo) {
        Category category=BeanCopyUtils.copyBean(categoryVo, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategory(Long id) {
        Category category=getById(id);
        CategoryVo categoryVo=BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryVo categoryVo) {
        Category category=BeanCopyUtils.copyBean(categoryVo, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResponseResult changeCategory(CategoryVo categoryVo) {
        LambdaUpdateWrapper<Category> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(Category::getStatus,categoryVo.getStatus())
                .eq(Category::getId,categoryVo.getId());
        categoryMapper.update(null,updateWrapper);
        return ResponseResult.okResult();
    }

}
