package com.timi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Long id;
    private String userName;
    private String nickName;
    private String password;

    private String status;

    private String email;

    private String phonenumber;

    private String sex;

    private List<Long> roleIds;
}
