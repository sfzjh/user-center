package com.sfzjh.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sfzjh.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:43
 */
public interface UserService extends IService<User> {

    /**
     * 注册用户
     *
     * @param username 账号
     * @param password 密码
     * @param checkPassword 校验密码
     * @param planetCode 星球编号
     * @return java.lang.Long 用户id
     */
    Long registerUser(String username, String password, String checkPassword, String planetCode);

    /**
     * 登录用户
     *
     * @param username 账号
     * @param password 密码
     * @param request 请求信息
     * @return com.sfzjh.entity.User 脱敏后的用户信息
     */
    User loginUser(String username, String password, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser 原始用户信息
     * @return com.sfzjh.entity.User 脱敏后的用户信息
     */
    User desensitizeUser(User originUser);

    /**
     * 注销用户登录
     *
     * @param request 请求信息
     */
    void logoutUser(HttpServletRequest request);


}
