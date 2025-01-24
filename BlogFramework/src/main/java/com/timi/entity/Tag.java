package com.timi.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * 标签(Tag)实体类
 *
 * @author makejava
 * @since 2024-12-31 11:42:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag{
//    private static final long serialVersionUID = -80598293263479025L;
    @TableId
    private Long id;
    /**
     * 标签名
     */
    private String name;
    @TableField(fill=FieldFill.INSERT)
    private Long createBy;
    @TableField(fill=FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;
    /**
     * 备注
     */
    private String remark;

}

