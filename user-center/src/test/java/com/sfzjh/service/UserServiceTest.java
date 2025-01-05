package com.sfzjh.service;


import com.sfzjh.common.Sm4Utils;
import com.sfzjh.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author 孙飞
 * @Date 2024年12月28日 10:44
 * @PackageName com.sfzjh.service
 * @Name UserServiceTest
 * @Version 1.0
 * @Description 用户服务测试类
 * Created with IntelliJ IDEA.
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService  userService;

    /**
     * 添加新用户测试
     */
    @Test
    public void testSaveUser() {
        User user = new User();
        user.setNickname("孙飞");
        user.setUsername("admin");
        user.setGender(0);
        user.setPassword("123456");
        user.setAvatarUrl("https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png");
        user.setPhone("18682710037");
        user.setEmail("admin@qq.com");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    /**
     * 测试获取用户
     */
    @Test
    public void testGetUser() {
        User user = userService.getById(3688177892430184448L);
        Assertions.assertNotNull(user);
    }

    /**
     * 测试登录用户
     */
    @Test
    public void testLoginUser() {
        User user = userService.loginUser("admin", "123456", null);
        System.out.println(user);
    }


    /**
     * 测试SM4加密和解密
     */
    @Test
    public void testSM4() {
        String str =  Sm4Utils.encryptSm4("123456");
        System.out.println("加密后的字符串为："+str);
        System.out.println("解密后的字符串为："+Sm4Utils.decryptSm4("bddb7083b8fc2d05611563e2fc385645"));
    }
}
