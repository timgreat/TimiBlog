package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.entity.Comment;
import com.timi.exception.SystemException;
import com.timi.mapper.CommentMapper;
import com.timi.utils.*;
import com.timi.vo.CommentVo;
import com.timi.vo.PageVo;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService{
    @Autowired
    private UserService userService;
    //查询评论列表
    @Override
    public ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        //查询评论
        LambdaQueryWrapper<Comment> commentWrapper=new LambdaQueryWrapper<>();
        commentWrapper.eq(SysConstant.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        commentWrapper.eq(Comment::getRootId, SysConstant.ROOT_COMMENT);
        commentWrapper.eq(Comment::getType,commentType);
        //分页
        Page<Comment> pages=new Page<>(pageNum,pageSize);
        page(pages,commentWrapper);
        List<Comment> commentList=pages.getRecords();
        List<CommentVo> commentVos= toCommentVoList(commentList);
        for (CommentVo commentVo: commentVos){
            commentVo.setChildren(getChildren(commentVo.getId()));
        }
        PageVo pageVo=new PageVo(commentVos,pages.getTotal());
        return ResponseResult.okResult(pageVo);
    }
    //添加文章评论
    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTEXT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments=list(queryWrapper);
        List<CommentVo> commentVos=toCommentVoList(comments);
        return commentVos;
    }
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos=BeanCopyUtils.copyBeanList(list,CommentVo.class);
        commentVos.stream()
                .map(new Function<CommentVo, CommentVo>() {
                    @Override
                    public CommentVo apply(CommentVo commentVo) {
                        if(commentVo.getToCommentId()!=-1){
                            commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName());
                        }
                        commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName());
                        return commentVo;
                    }
                })
                .collect(Collectors.toList());
        return commentVos;
    }
}
