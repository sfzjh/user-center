package com.sfzjh.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册信息
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:41
 */
@Data
public class UserRegisterDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 校验密码
     */
    private String checkPassword;

    /**
     * 星球编号
     */
    private String planetCode;

}
