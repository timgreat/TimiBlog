package com.timi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private Long id;
    /**
     * 标签名
     */
    private String name;

//    private Long createBy;
//
//    private Date createTime;
//
//    private Long updateBy;
//
//    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
}
