package com.timi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfovo {
    private Long id;
    private String email;
    private String nickName;
    private String userName;
    private String sex;
    private String phonenumber;
    private String status;
}
