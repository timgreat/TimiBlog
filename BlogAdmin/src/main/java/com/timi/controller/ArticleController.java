package com.timi.controller;

import com.timi.dto.AddArticleDto;
import com.timi.service.ArticleService;
import com.timi.utils.ResponseResult;
import com.timi.vo.ArticleQuaryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("")
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String title,String summary){
        return articleService.list(pageNum,pageSize,title,summary);
    }
    @GetMapping("/{id}")
    public ResponseResult getInfo(@PathVariable("id")Long id){
        return articleService.getInfo(id);
    }
    @PutMapping("")
    public ResponseResult update(@RequestBody ArticleQuaryVo articleQuaryVo){
        return articleService.update(articleQuaryVo);
    }
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long id){
        return articleService.delete(id);
    }
}
