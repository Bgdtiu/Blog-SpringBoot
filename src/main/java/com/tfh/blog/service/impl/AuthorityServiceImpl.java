package com.tfh.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tfh.blog.pojo.Authority;
import com.tfh.blog.service.AuthorityService;
import com.tfh.blog.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;

/**
* @author B
* @description 针对表【authority】的数据库操作Service实现
* @createDate 2024-05-20 17:30:07
*/
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority>
    implements AuthorityService{

}




