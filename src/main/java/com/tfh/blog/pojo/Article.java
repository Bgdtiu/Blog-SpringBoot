package com.tfh.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Long userId;

    private Date creationTime;

    private Date changeTime;

    private Integer count;

    private Integer status;

    private String title;

    private String content;

    private static final long serialVersionUID = 1L;
}