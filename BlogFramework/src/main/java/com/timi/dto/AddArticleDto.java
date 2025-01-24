package com.timi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleDto {
    private Long id;
    private String title;
    private String summary;
    private  Long categoryId;
    private  String content;
    private String thumbnail;
    private Long viewCount;
    private String isTop;
    private String status;
    //是否允许评论
    private String isComment;
    private List<Long> tags;
}
