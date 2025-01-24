package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.dto.AddArticleDto;
import com.timi.entity.Article;
import com.timi.utils.ResponseResult;
import com.timi.vo.ArticleQuaryVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);
    ResponseResult list(Integer pageNum,Integer pageSize,String title,String summary);

    ResponseResult getInfo(Long id);

    ResponseResult update(ArticleQuaryVo articleQuaryVo);

    ResponseResult delete(Long id);
}
