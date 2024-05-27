package com.tfh.blog.mapper;

import com.tfh.blog.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author B
* @description 针对表【article】的数据库操作Mapper
* @createDate 2024-05-23 17:05:15
* @Entity com.tfh.blog.pojo.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 个人文章数,如果还有别的数量可以在这里继续添加
     * @param userId
     * @return List集合
     */
    List<Integer> variableByUserId(@Param("userId") Long userId);

    /**
     * 获取未审核数
     * @return int
     */
    Integer getHasNotBeenReviewed();

    /**
     * 获取审核失败数
     * @return int
     */
    Integer auditFailure();

    /**
     * 审核通过数
     * @return int
     */
    Integer examinationPassed();
}




