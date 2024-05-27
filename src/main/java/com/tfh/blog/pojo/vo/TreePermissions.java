package com.tfh.blog.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/21 19:17
 * TODO: 树权限
 */
@Data
public class TreePermissions {
    private String  key;
    private String label;
    private List<TreePermissions> children;
}
