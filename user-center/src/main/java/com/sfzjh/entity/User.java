package com.sfzjh.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:42
 */
@TableName(value = "user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 账号
     */
    private String username;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0：正常
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 0：普通用户  1：管理员
     */
    private Integer userRole;

    /**
     * 星球编号
     */
    private String planetCode;

}
