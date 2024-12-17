package com.timi.controller;

import com.timi.entity.Article;
import com.timi.service.ArticleService;
import com.timi.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return  result;
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(){
        ResponseResult result=articleService.articleList();
        return result;
    }

}
