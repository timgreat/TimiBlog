package com.timi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.entity.User;
import com.timi.mapper.UserMapper;

import com.timi.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class BlogLoginServiceImpl extends ServiceImpl<UserMapper, User> implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public ResponseResult login(User user) {

        return null;
    }
}
