package com.timi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timi.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<String> selectRoleKeyByUserId(Long userId);

    void addRoleMenu(@Param("roleId") Long id, @Param("menuIds") List<Long> menuIds);

    void deleteRoleMenu(@Param("roleId")Long id);

}

