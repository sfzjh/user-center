package com.sfzjh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sfzjh.common.BaseResponse;
import com.sfzjh.common.ErrorCode;
import com.sfzjh.common.ResultUtils;
import com.sfzjh.constant.UserConstant;
import com.sfzjh.dto.UserLoginDTO;
import com.sfzjh.dto.UserRegisterDTO;
import com.sfzjh.entity.User;
import com.sfzjh.exception.BusinessException;
import com.sfzjh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户控制器
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29 21:32
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 注册用户
     *
     * @param userRegisterDTO 用户注册信息
     * @return {@link BaseResponse<Long>} 成功后返回用户id
     */
    @PostMapping("/register")
    public BaseResponse<Long> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        // 校验
        if (userRegisterDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();
        String planetCode = userRegisterDTO.getPlanetCode();
        if (StringUtils.isAnyBlank(username, password, checkPassword, planetCode)) {
            return null;
        }
        Long result = userService.registerUser(username, password, checkPassword, planetCode);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO 用户登录信息
     * @param request 请求
     * @return {@link BaseResponse<User>} 返回用户信息
     */
    @PostMapping("/login")
    public BaseResponse<User> loginUser(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        if (userLoginDTO == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.loginUser(username, password, request);
        return ResultUtils.success(user);

    }

    /**
     * 注销用户登录
     *
     * @param request 请求
     * @return {@link BaseResponse<>}
     */
    @PostMapping("/logout")
    public BaseResponse<?> logoutUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.logoutUser(request);
        return ResultUtils.success(null);
    }

    /**
     * 获取当前用户信息
     *
     * @param request 当前请求
     * @return {@link BaseResponse<User>}
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUserInfo(HttpServletRequest request) {
        Object attributeUser = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) attributeUser;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Long userId = currentUser.getId();
        // 校验用户是否合法
        User user = userService.getById(userId);
        User desensitizeUser = userService.desensitizeUser(user);
        return ResultUtils.success(desensitizeUser);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @param request servlet请求
     * @return {@link BaseResponse<List<User>>} 用户信息
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> listUsers(String username, HttpServletRequest request) {
        if (!checkAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(User::getUsername, username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> desensitizeUserList = userList.stream().map(userService::desensitizeUser).collect(Collectors.toList());
        return ResultUtils.success(desensitizeUserList);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @param request servlet请求
     * @return {@link BaseResponse<Boolean>} 是否删除成功
     */
    @PostMapping("/remove")
    public BaseResponse<Boolean> removeUser(@RequestBody Long id, HttpServletRequest request) {
        if (!checkAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     *
     *
     * @param request 请求信息
     * @return {@link Boolean} 是否是管理员
     */
    private Boolean checkAdmin(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) object;
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }

}
