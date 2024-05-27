package com.tfh.blog.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/24 19:02
 * TODO: 审核文章
 */
@Data
public class ModerateArticles {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String userName;

    private Integer articleId;

    private String creationTime;

    private Integer count;

    private String title;

    private String content;

}
