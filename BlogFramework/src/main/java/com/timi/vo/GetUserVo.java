package com.timi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import com.timi.entity.Role;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private GetUserInfovo user;
}
