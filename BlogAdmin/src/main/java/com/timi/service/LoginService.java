package com.timi.service;

import com.timi.entity.User;
import com.timi.utils.ResponseResult;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
