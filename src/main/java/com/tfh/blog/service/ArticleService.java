package com.tfh.blog.service;

import com.tfh.blog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tfh.blog.utils.result.Result;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author B
 * @description 针对表【article】的数据库操作Service
 * @createDate 2024-05-23 17:05:15
 */
public interface ArticleService extends IService<Article> {

    /**
     * 返回当前用户数据
     *
     * @param req     HttpServletRequest
     * @param current 页数，不是必须的
     * @return 返回 Result
     */
    Result getArticle(HttpServletRequest req, int current);

    /**
     * 添加用户文章
     *
     * @param article 当前文章
     * @param req     HttpServletRequest
     * @return Result
     */
    Result createArticle(Article article, HttpServletRequest req);

    /**
     * 修改当前用户文章数据
     *
     * @param article 文章数据
     * @param req     HttpServletRequest
     * @return Result
     */
    Result updateArticle(Article article, HttpServletRequest req);

    /**
     * 根据用户删除当前的文章
     *
     * @param id  文章id
     * @param req 获取用户id
     * @return Result
     */
    Result deleteArticle(Integer id, HttpServletRequest req);

    /**
     * 获取需要审核的文章
     *
     * @return Result
     */
    Result getAuditData();


    /**
     * 审核通过或者驳回
     *
     * @param value     值
     * @param articleId 文章id
     * @return Result
     */
    Result passOrReject(Integer value, Integer articleId);

    /**
     * 返回给 home 主页数据
     * @param page 当前页
     * @return Result
     */
    Result publicArticle(Integer page);
}
