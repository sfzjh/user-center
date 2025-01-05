package com.sfzjh.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录信息
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29 20:30
 */
@Data
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
