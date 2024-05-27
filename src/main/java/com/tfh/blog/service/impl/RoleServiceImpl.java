package com.tfh.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tfh.blog.pojo.Role;
import com.tfh.blog.service.RoleService;
import com.tfh.blog.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author B
* @description 针对表【role】的数据库操作Service实现
* @createDate 2024-05-19 01:48:51
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




