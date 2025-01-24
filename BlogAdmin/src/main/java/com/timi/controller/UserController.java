package com.timi.controller;

import com.timi.dto.ChangeUserStatusDto;
import com.timi.dto.UserDto;
import com.timi.entity.Role;
import com.timi.service.RoleService;
import com.timi.service.UserService;
import com.timi.utils.ResponseResult;
import com.timi.vo.GetUserInfovo;
import com.timi.vo.GetUserVo;
import com.timi.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public ResponseResult listUser(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status){
        return userService.listUser(pageNum,pageSize,userName,phonenumber,status);
    }
    @PostMapping("")
    public ResponseResult addUser(@RequestBody UserVo userVo){
        return userService.addUser(userVo);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id")Long id){
        return userService.deleteUser(id);
    }
    @Autowired
    private RoleService roleService;
    @GetMapping("/{id}")
    public ResponseResult getUserInfo(@PathVariable("id")Long id){
        List<Long> roleIds=userService.getRoleIds(id);
        List<Role> roles=roleService.listAllRole();
        GetUserInfovo user=userService.getUser(id);
        GetUserVo getUserVo=new GetUserVo(roleIds,roles,user);
        return ResponseResult.okResult(getUserVo);
    }
    @PutMapping("")
    public ResponseResult updateUser(@RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeUserStatusDto userStatusDto){
        return userService.changeStatus(userStatusDto);
    }
}
