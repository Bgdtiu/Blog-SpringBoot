package com.tfh.blog.service;

import com.tfh.blog.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tfh.blog.pojo.vo.ModifyPermissions;
import com.tfh.blog.utils.result.Result;

/**
 * @author B
 * @description 针对表【user】的数据库操作Service
 * @createDate 2024-05-19 01:48:51
 */
public interface UserService extends IService<User> {

    /**
     * 注册接口
     *
     * @param user 返回的 账号密码
     * @return Result
     */
    Result registered(User user);

    /**
     * 返回所有用户
     *
     * @return Result
     */
    Result userAll();

    /**
     * 根据 userId 获取当前用户信息
     *
     * @param tokenById 用户Id
     * @return Result
     */
    Result userInfo(Long tokenById);

    /**
     * 根据 administrator:get_user_Competence 权限，获取所有人的权限信息
     *
     * @return Result
     */
    Result getUserPermissions(Object searchInformation, Integer count, int current);


    Result putUserPermissions(ModifyPermissions modifyPermissions);
}
