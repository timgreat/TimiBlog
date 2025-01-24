package com.timi.controller;

import com.timi.entity.Comment;
import com.timi.service.CommentService;
import com.timi.utils.ResponseResult;
import com.timi.utils.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SysConstant.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }
    @PostMapping("")
    public ResponseResult addComment(@RequestBody Comment comment){

        return commentService.addComment(comment);
    }
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SysConstant.LINK_COMMENT,null,pageNum,pageSize);
    }
}
