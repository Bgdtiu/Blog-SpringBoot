package com.tfh.blog.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/22 15:00
 * TODO: 修改权限类
 */
@Data
public class ModifyPermissions {
    private Long  userId;
    private List<String> permissions;
}
