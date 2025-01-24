package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.dto.ChangeUserStatusDto;
import com.timi.dto.UserDto;
import com.timi.entity.User;
import com.timi.exception.SystemException;
import com.timi.mapper.UserMapper;
import com.timi.utils.AppHttpCodeEnum;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.utils.SecurityUtils;
import com.timi.vo.GetUserInfovo;
import com.timi.vo.PageVo;
import com.timi.vo.UserInfoVo;
import com.timi.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseResult userInfo() {
        String userId=SecurityUtils.getUserId().toString();
        User user=getById(userId);
        UserInfoVo userInfoVo=BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        LambdaUpdateWrapper<User> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getAvatar,user.getAvatar());
        updateWrapper.set(User::getNickName,user.getNickName());
        updateWrapper.set(User::getSex,user.getSex());
        updateWrapper.eq(User::getId,user.getId());
        userMapper.update(null,updateWrapper);
        return ResponseResult.okResult();
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断 null或”“
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(userNickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //对密码进行加密
        String encodePassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean userNickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }
    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;
    }
    private boolean phoneNumberExist(String phoneNumber) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phoneNumber);
        return count(queryWrapper)>0;
    }
    @Override
    public ResponseResult listUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userName),User::getUserName,userName);
        queryWrapper.like(StringUtils.hasText(phonenumber),User::getPhonenumber,phonenumber);
        queryWrapper.eq(StringUtils.hasText(status),User::getStatus,status);
        Page<User> pages=new Page<>(pageNum,pageSize);
        page(pages,queryWrapper);
        PageVo pageVo=new PageVo(pages.getRecords(),pages.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addUser(UserVo userVo) {
        //对数据进行非空判断 null或”“
        if(!StringUtils.hasText(userVo.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(userVo.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(userVo.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(emailExist(userVo.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if(phoneNumberExist(userVo.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //对密码进行加密
        User user=BeanCopyUtils.copyBean(userVo,User.class);
        String encodePassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        if (!userVo.getRoleIds().isEmpty()){
            userMapper.insertUserRole(user.getId(),userVo.getRoleIds());
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public GetUserInfovo getUser(Long id) {
        GetUserInfovo getUserInfovo=BeanCopyUtils.copyBean(getById(id), GetUserInfovo.class);
        return getUserInfovo;
    }

    @Override
    public List<Long> getRoleIds(Long id) {
        List<Long> roleIds=userMapper.selectUserRole(id);
        return roleIds;
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UserDto userDto) {
        User user=BeanCopyUtils.copyBean(userDto,User.class);
        updateById(user);
        userMapper.deleteUserRole(userDto.getId());
        if (!userDto.getRoleIds().isEmpty()){
            userMapper.insertUserRole(userDto.getId(),userDto.getRoleIds());
        }
        return null;
    }

    @Override
    public ResponseResult changeStatus(ChangeUserStatusDto userStatusDto) {
        LambdaUpdateWrapper<User> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getStatus,userStatusDto.getStatus());
        updateWrapper.eq(User::getId,userStatusDto.getUserId());
        userMapper.update(null,updateWrapper);
        return ResponseResult.okResult();
    }
}
