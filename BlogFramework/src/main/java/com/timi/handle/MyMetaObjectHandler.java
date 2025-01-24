package com.timi.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.timi.utils.SecurityUtils;
import com.timi.utils.SysConstant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
//Mybatis-plus的自动填充功能，可以在用户添加评论时，对评论的信息进行自动填充
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId=null;
//        userId= SecurityUtils.getUserId();
        try {
            userId= SecurityUtils.getUserId();
        }catch (Exception e){
//            e.printStackTrace();
            userId= SysConstant.NOBODY_ID;
        }
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("createBy",userId,metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("updateBy",userId,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId=null;
        try {
            userId= SecurityUtils.getUserId();
        }catch (Exception e){
//            e.printStackTrace();
            userId= SysConstant.NOBODY_ID;
        }
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }
}
