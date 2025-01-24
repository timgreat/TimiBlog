package com.timi.utils;

import com.timi.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static LoginUser getLoginUser(){
        return (LoginUser) getAuthentication().getPrincipal();
    }
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public static  Long getUserId(){
        return getLoginUser().getUser().getId();
    }
    public static Boolean isAdmin(){
        Long id=getUserId();
        return (id!=null)&&(id.equals(SysConstant.ADMIN_ID));
    }
}
