package com.timi.service;

import com.timi.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {
    public boolean hasPermission(String permission){
        //超级管理员，直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        //否则获取当前登录用户所具有的权限列表
        List<String> permissions=SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
