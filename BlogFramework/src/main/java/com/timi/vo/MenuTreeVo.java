package com.timi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeVo {
    private Long id;
    /**
     * 菜单名称
     */
    private String label;
    /**
     * 父菜单ID
     */
    private Long parentId;
    private List<MenuTreeVo> children;
}
