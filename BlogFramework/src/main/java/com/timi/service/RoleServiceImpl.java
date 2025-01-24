package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.dto.ChangeRoleStatusDto;
import com.timi.dto.RoleDto;
import com.timi.entity.Role;
import com.timi.mapper.RoleMapper;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.utils.SysConstant;
import com.timi.vo.MenuTreeVo;
import com.timi.vo.PageVo;
import com.timi.vo.RoleMenuTreeVo;
import com.timi.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //如果是管理员，返回admin
        if(id==1L){
            List<String> list=new ArrayList<>();
            list.add("admin");
            return list;
        }
        return roleMapper.selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult listRoles(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName),Role::getRoleName,roleName);
        queryWrapper.eq(StringUtils.hasText(status),Role::getStatus,status);
        Page<Role> pages=new Page<>(pageNum,pageSize);
        page(pages,queryWrapper);
        PageVo pageVo=new PageVo(pages.getRecords(),pages.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(ChangeRoleStatusDto dto) {
        UpdateWrapper<Role> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("status",dto.getStatus());
        updateWrapper.eq("id",dto.getRoleId());
        roleMapper.update(null,updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Long id) {
        RoleVo roleVo= BeanCopyUtils.copyBean(getById(id), RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    @Transactional
    public ResponseResult addRole(RoleDto roleDto) {
        Role role=BeanCopyUtils.copyBean(roleDto,Role.class);
        save(role);
        if(!roleDto.getMenuIds().isEmpty()){
            roleMapper.addRoleMenu(role.getId(),roleDto.getMenuIds());
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult updateRole(RoleDto roleDto) {
        Role role=BeanCopyUtils.copyBean(roleDto,Role.class);
        roleMapper.updateById(role);
        roleMapper.deleteRoleMenu(role.getId());
        if (!roleDto.getMenuIds().isEmpty()){
            roleMapper.addRoleMenu(role.getId(),roleDto.getMenuIds());
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteRole(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<Role> listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SysConstant.STATUS_NORMAL);
        List<Role> roles=list(queryWrapper);
        return roles;
    }
}
