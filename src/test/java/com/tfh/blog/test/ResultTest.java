package com.tfh.blog.test;

import com.tfh.blog.utils.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/19 0:11
 * TODO: test
 */
@SpringBootTest
public class ResultTest {

    @Test
    public void test01() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");
        Result ok = Result.ok(strings);
        System.out.println(ok);

    }
}
