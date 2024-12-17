package com.timi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @TableId
    private Long id;
    private String name;
    private Long pid;
    private String description;
    private String status;
    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;
}
