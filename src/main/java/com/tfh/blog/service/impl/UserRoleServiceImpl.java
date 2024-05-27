package com.tfh.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tfh.blog.pojo.UserRole;
import com.tfh.blog.service.UserRoleService;
import com.tfh.blog.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author B
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2024-05-19 01:48:51
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




