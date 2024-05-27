package com.tfh.blog.mapper;

import com.tfh.blog.pojo.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author B
 * @description 针对表【user_info】的数据库操作Mapper
 * @createDate 2024-05-19 01:48:51
 * @Entity com.tfh.blog.pojo.UserInfo
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 初始化用户信息
     *
     * @param userId 用户id
     */
    void initialUserInformation(@Param("userId") Long userId);

    /**
     * 返回用户信息
     *
     * @param userId 用户id
     * @return
     */
    UserInfo getUserInfoById(@Param("userId") Long userId);

}




