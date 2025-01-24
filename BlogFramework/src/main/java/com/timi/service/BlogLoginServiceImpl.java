package com.timi.service;


import com.timi.entity.LoginUser;
import com.timi.entity.User;


import com.timi.utils.BeanCopyUtils;
import com.timi.utils.JwtUtil;
import com.timi.utils.RedisCache;
import com.timi.utils.ResponseResult;
import com.timi.vo.BlogUserLoginVo;
import com.timi.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
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
        redisCache.setCacheObject("bloglogin:"+userid,loginUser);
        //user转化为userInfoVo
        UserInfoVo userInfoVo= BeanCopyUtils.copyBean(loginUser.getUser(),UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo=new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult loginout() {
        //获取token，解析出userid
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser= (LoginUser) authentication.getPrincipal();
        //获取userid
        String userid=loginUser.getUser().getId().toString();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userid);
        return ResponseResult.okResult();
    }
}
