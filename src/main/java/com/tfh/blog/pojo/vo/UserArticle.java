package com.tfh.blog.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/24 17:40
 * TODO: 用户文章
 */
@Data
public class UserArticle implements Serializable {
    private Integer id;

    private String creationTime;

    private String changeTime;

    private Integer status;

    private String title;

    private String content;

    private static final long serialVersionUID = 1L;
}
