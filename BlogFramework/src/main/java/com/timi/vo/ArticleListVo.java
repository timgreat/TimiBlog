package com.timi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {
    private Long id;
    private String title;
    private String summary;
    private Long categoryId;
    private  String categoryName;
    private String thumbnail;
    private Long viewCount;
    private Date createTime;

}
