package com.tfh.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tfh.blog.pojo.UserInfo;
import com.tfh.blog.service.UserInfoService;
import com.tfh.blog.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author B
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2024-05-19 01:48:51
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




