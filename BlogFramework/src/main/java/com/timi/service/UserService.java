package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timi.dto.ChangeUserStatusDto;
import com.timi.dto.UserDto;
import com.timi.entity.User;
import com.timi.utils.ResponseResult;
import com.timi.vo.GetUserInfovo;
import com.timi.vo.UserVo;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface UserService extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult listUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(UserVo userVo);

    ResponseResult deleteUser(Long id);

    GetUserInfovo getUser(Long id);

    List<Long> getRoleIds(Long id);

    ResponseResult updateUser(UserDto userDto);

    ResponseResult changeStatus(ChangeUserStatusDto userStatusDto);
}
