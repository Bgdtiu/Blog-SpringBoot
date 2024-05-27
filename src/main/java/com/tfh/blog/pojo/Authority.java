package com.tfh.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName authority
 */
@TableName(value ="authority")
@Data
public class Authority implements Serializable {
    private Integer id;

    private Integer roleId;

    private String permissionName;

    private String description;

    private static final long serialVersionUID = 1L;
}