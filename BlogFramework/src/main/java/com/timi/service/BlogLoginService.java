package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timi.entity.User;
import com.timi.utils.ResponseResult;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult loginout();
}
