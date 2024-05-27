package com.tfh.blog.mapper;

import com.tfh.blog.pojo.Authority;
import com.tfh.blog.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author B
* @description 针对表【role】的数据库操作Mapper
* @createDate 2024-05-19 01:48:51
* @Entity com.tfh.blog.pojo.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取角色信息
     * @return Role 集合
     */
    List<Role> getRoleInformation();

    /**
     * 根据角色id 返回所有权限信息
     * @param roleId 角色id
     * @return Authority集合
     */
    List<Authority> getPermissionInformation(@Param("roleId") Integer roleId);

    /**
     * 根据用户id 获取角色
     * @param userId
     * @return
     */
    List<String> getRoleBasedOnUserId(@Param("userId") Long userId);
}




