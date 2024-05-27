package com.tfh.blog.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tfh.blog.mapper.UserMapper;
import com.tfh.blog.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/19 1:42
 * TODO:
 */
@SpringBootTest
public class MybatisPlusTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test01() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
}
