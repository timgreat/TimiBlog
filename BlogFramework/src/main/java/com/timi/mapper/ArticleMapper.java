package com.timi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timi.entity.Article;
import com.timi.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    void addArticleTag(@Param("articleId") Long articleId, @Param("tagIds")List<Long> tagIds);
    List<Long> selectArticleTag(@Param("articleId") Long articleId);
    void deleteArticleTag(@Param("articleId") Long articleId);
}
