package com.timi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String nickName;
    private String status;

    private String email;

    private String sex;
    private String phonenumber;
    private List<Long> roleIds;
}
