package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timi.dto.ChangeRoleStatusDto;
import com.timi.dto.RoleDto;
import com.timi.entity.Role;
import com.timi.utils.ResponseResult;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult listRoles(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(ChangeRoleStatusDto dto);

    ResponseResult getRole(Long id);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult updateRole(RoleDto roleDto);

    ResponseResult deleteRole(Long id);

    List<Role> listAllRole();
}
