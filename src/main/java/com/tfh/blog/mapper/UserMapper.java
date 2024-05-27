package com.tfh.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tfh.blog.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author B
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2024-05-19 01:48:51
 * @Entity com.tfh.blog.pojo.User
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名获取id
     *
     * @param userName 用户名
     * @return id
     */
    long getIdBasedOnUserName(@Param("userName") String userName);

    /**
     * 获取用户的权限信息
     *
     * @param userId 用户id
     * @return 返回 String 类型的 List 集合
     */
    List<String> getUserAuthentication(@Param("userId") Long userId);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 返回当前用户，如果没有则返回 null
     */
    User getUserBasedOnUserName(@Param("userName") String userName);

}




