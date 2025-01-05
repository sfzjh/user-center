package com.sfzjh.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sfzjh.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表数据交互
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:43
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
