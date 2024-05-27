package com.tfh.blog.mapper;

import com.tfh.blog.pojo.Role;
import com.tfh.blog.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
* @author B
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2024-05-19 01:48:51
* @Entity com.tfh.blog.pojo.UserRole
*/
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 初始化用户权限
     * @param userId 用户id
     */
    void initialRole(@Param("userId") Long userId);

    /**
     * 删除用户所有角色
     * @param userId 用户id
     */
    void deleteUserAllRole(@Param("userId") Long userId);

    /**
     * 添加用户角色
     * @param userId 用户id
     * @param roles 角色id
     */
    void addUserRole(@Param("userId") Long userId, @Param("roles") List<Role> roles);
}




