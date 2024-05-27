package com.tfh.blog.mapper;

import com.tfh.blog.pojo.Authority;
import com.tfh.blog.pojo.BlacklistAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author B
* @description 针对表【blacklist_authority】的数据库操作Mapper
* @createDate 2024-05-19 01:48:51
* @Entity com.tfh.blog.pojo.BlacklistAuthority
*/
public interface BlacklistAuthorityMapper extends BaseMapper<BlacklistAuthority> {


    /**
     * 删除当前用户所有的黑名单
     * @param userId 用户id
     */
    void deleteUserAllBlacklist(@Param("userId") Long userId);

    /**
     * 添加当前用户黑名单
     *
     * @param userId      用户id
     * @param authorities 需要添加的黑名单权限
     */
    void addUserBlacklist(@Param("userId") Long userId, @Param("authorities") List<Authority> authorities);
}




