package com.tfh.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName blacklist_authority
 */
@TableName(value ="blacklist_authority")
@Data
public class BlacklistAuthority implements Serializable {
    private Integer id;

    private Long userId;

    private static final long serialVersionUID = 1L;
}