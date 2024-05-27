package com.tfh.blog.test;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.Arrays;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/19 18:07
 * TODO: test
 */
@SpringBootTest
public class PublicTest {
    @Test
    public void test01() {
        byte[] bytes = RandomUtil.randomBytes(512);
        System.out.println(Arrays.toString(bytes));

    }

    @Test
    public void test02() {
        User.UserBuilder root = User.withDefaultPasswordEncoder().username("root").password("123321");
        System.out.println(root);

        String encode = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123");


//        AuthorityUtils.commaSeparatedStringToAuthorityList()
    }
}