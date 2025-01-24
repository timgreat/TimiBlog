package com.timi.controller;

import com.timi.entity.LoginUser;
import com.timi.entity.User;
import com.timi.exception.SystemException;
import com.timi.service.LoginService;
import com.timi.service.MenuService;
import com.timi.service.RoleService;
import com.timi.utils.AppHttpCodeEnum;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.utils.SecurityUtils;
import com.timi.vo.AdminUserInfoVo;
import com.timi.vo.MenuVo;
import com.timi.vo.RoutersVo;
import com.timi.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示要传递用户名
//            throw new RuntimeException("");
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        //获取当前登录用户
        LoginUser loginUser= SecurityUtils.getLoginUser();
        //根据用户id获取权限信息
        List<String> perms=menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roles=roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo=new AdminUserInfoVo(perms,roles, BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class));
        return ResponseResult.okResult(adminUserInfoVo);
    }
    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        //查询menu，结果为tree的形式
        Long userId=SecurityUtils.getUserId();
        List<MenuVo> menuVos=menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menuVos));
    }

}
