package com.tfh.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName role
 */
@TableName(value ="role")
@Data
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String roleName;

    private String description;

    private static final long serialVersionUID = 1L;
}