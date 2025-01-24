package com.timi.service;


import com.timi.entity.LoginUser;
import com.timi.entity.User;
import com.timi.utils.*;
import com.timi.vo.BlogUserLoginVo;
import com.timi.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authentication=authenticationManager.authenticate(authenticationToken);
        //认证是否通过
        if(Objects.isNull(authentication)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
        String userid=loginUser.getUser().getId().toString();
        String jwt=JwtUtil.createJWT(userid);
        //用户信息存入redis
        redisCache.setCacheObject("login:"+userid,loginUser);
        Map<String,String> map=new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取userid
        String userid= SecurityUtils.getUserId().toString();
        //删除redis中的用户信息
        redisCache.deleteObject("login:"+userid);
        return ResponseResult.okResult();
    }


}
