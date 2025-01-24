package com.timi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    void insertUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    List<Long> selectUserRole(@Param("userId") Long id);

    void deleteUserRole(@Param("userId")Long id);
}
