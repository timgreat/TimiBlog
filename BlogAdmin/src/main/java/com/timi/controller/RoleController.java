package com.timi.controller;

import com.timi.dto.ChangeRoleStatusDto;
import com.timi.dto.RoleDto;
import com.timi.service.MenuService;
import com.timi.service.RoleService;
import com.timi.utils.ResponseResult;
import com.timi.vo.MenuTreeVo;
import com.timi.vo.RoleMenuTreeVo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult listRoles(Integer pageNum,Integer pageSize, String roleName,String status){
        return roleService.listRoles(pageNum,pageSize,roleName,status);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto dto){
        return roleService.changeStatus(dto);
    }
    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable("id") Long id){
        return roleService.getRole(id);
    }
    @PostMapping("")
    public ResponseResult addRole(@RequestBody RoleDto roleDto){
        return roleService.addRole(roleDto);
    }
    @PutMapping("")
    public ResponseResult updateRole(@RequestBody RoleDto roleDto){
        return roleService.updateRole(roleDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id){
        return roleService.deleteRole(id);
    }
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return ResponseResult.okResult(roleService.listAllRole());
    }
}
