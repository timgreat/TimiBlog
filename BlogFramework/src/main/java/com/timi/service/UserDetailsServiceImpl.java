package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.timi.entity.LoginUser;
import com.timi.entity.User;
import com.timi.mapper.MenuMapper;
import com.timi.mapper.UserMapper;
import com.timi.utils.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user=userMapper.selectOne(queryWrapper);
        //判断是否查到用户
        if(Objects.isNull(user)){
            throw  new RuntimeException("用户不存在");
        }
        //返回用户信息

        //TODO 查询权限信息封装
        if(user.getType().equals(SysConstant.ADMIN)){
            List<String> list=null;
            if(user.getId().equals(SysConstant.ADMIN_ID)){
                list=menuMapper.getAllPerms();

            }else{
                list=menuMapper.selectPermsByUserId(user.getId());
            }
            return new LoginUser(user,list);
        }
        return new LoginUser(user);
    }
}
