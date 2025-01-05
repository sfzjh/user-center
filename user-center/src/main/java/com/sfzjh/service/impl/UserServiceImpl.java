package com.sfzjh.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sfzjh.common.ErrorCode;
import com.sfzjh.common.Sm4Utils;
import com.sfzjh.constant.UserConstant;
import com.sfzjh.entity.User;
import com.sfzjh.exception.BusinessException;
import com.sfzjh.mapper.UserMapper;
import com.sfzjh.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户服务实现类
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:43
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    /**
     * 注册用户
     *
     * @param username 账号
     * @param password 密码
     * @param checkPassword 校验密码
     * @param planetCode 星球编号
     * @return java.lang.Long 用户id
     */
    @Override
    public Long registerUser(String username, String password, String checkPassword, String planetCode) {
        // 1.校验
        if (StringUtils.isAnyBlank(username, password, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (username.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名长度是6位及以上");
        }
        if (password.length() < 6 || checkPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度是6位及以上");
        }
        if (planetCode.length() > 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号长度是5位以下");
        }
        // 账户名不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if (matcher.find()) {
            return -1L;
        }
        // 两次输入的密码要相同
        if (!password.equals(checkPassword)) {
            return -1L;
        }
        // 用户名不能重复
        Long count = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名已存在");
        }
        // 星球编号不能重复
        count = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getPlanetCode, planetCode));
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号已存在");
        }
        // 2.设置用户并且密码加密
        User user = new User();
        user.setUsername(username);
        user.setPassword(Sm4Utils.encryptSm4(password));
        user.setPlanetCode(planetCode);
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return user.getId();
        }
        return -1L;
    }

    /**
     * 登录用户
     *
     * @param username 用户名
     * @param password 密码
     * @param request 请求
     * @return {@link User} 用户信息
     */
    @Override
    public User loginUser(String username, String password, HttpServletRequest request) throws BusinessException, RuntimeException {
        // 1.校验
        if (StringUtils.isAnyBlank(username, password)) {
            return null;
        }
        if (username.length() < 4) {
            return null;
        }
        if (password.length() < 6) {
            return null;
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if (matcher.find()) {
            return null;
        }
        // 2. 获取用户并验证密码

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        if (user == null) {
            log.info("username not exist");
            return null;
        }
        String decryptPassword = Sm4Utils.decryptSm4(user.getPassword());
        if (!decryptPassword.equals(password)) {
            log.info("user login failed, username cannot match password");
            return null;
        }
        // 3.脱敏用户信息即不返回密码
        User desensitizeUser = desensitizeUser(user);
        // 4.记住用户的登录状态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, desensitizeUser);
        return desensitizeUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUser 原始用户信息
     * @return com.sfzjh.entity.User 脱敏后的用户信息
     */
    @Override
    public User desensitizeUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User desensitizeUser = new User();
        desensitizeUser.setId(originUser.getId());
        desensitizeUser.setUsername(originUser.getUsername());
        desensitizeUser.setNickname(originUser.getNickname());
        desensitizeUser.setAvatarUrl(originUser.getAvatarUrl());
        desensitizeUser.setGender(originUser.getGender());
        desensitizeUser.setPhone(originUser.getPhone());
        desensitizeUser.setEmail(originUser.getEmail());
        desensitizeUser.setUserStatus(originUser.getUserStatus());
        desensitizeUser.setUserRole(originUser.getUserRole());
        desensitizeUser.setPlanetCode(originUser.getPlanetCode());
        desensitizeUser.setCreateTime(originUser.getCreateTime());
        return desensitizeUser;
    }

    /**
     * 注销用户登录
     *
     * @param request 请求信息
     */
    @Override
    public void logoutUser(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
    }


}
