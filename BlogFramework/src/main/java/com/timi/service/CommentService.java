package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timi.entity.Comment;
import com.timi.utils.ResponseResult;

public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

}
