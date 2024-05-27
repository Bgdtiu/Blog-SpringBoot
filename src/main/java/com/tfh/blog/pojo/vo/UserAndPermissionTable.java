package com.tfh.blog.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/21 19:41
 * TODO: 用户和权限表
 */
@Data
public class UserAndPermissionTable {
    @JsonSerialize(using = ToStringSerializer.class)
    private long userId;
    private String userName;
    private List<String> roleId;
    private List<String> grantedPermission;
    private List<String> checkedKeys;
}
