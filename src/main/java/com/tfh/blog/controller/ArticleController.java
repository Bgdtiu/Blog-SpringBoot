package com.tfh.blog.controller;

import com.tfh.blog.pojo.Article;
import com.tfh.blog.pojo.vo.PassAndReject;
import com.tfh.blog.service.ArticleService;
import com.tfh.blog.utils.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/23 16:56
 * TODO: 文章控制器
 */

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @GetMapping("getArticle")
    @PreAuthorize("hasAuthority('article:get_user_article')")
    public Result getArticle(@RequestParam(value = "current", defaultValue = "1") int current, HttpServletRequest req) {
        return articleService.getArticle(req, current);
    }

    @PostMapping("createArticle")
    @PreAuthorize("hasAuthority('article:insert')")
    public Result createArticle(@RequestBody Article article, HttpServletRequest req) {
        return articleService.createArticle(article, req);
    }

    @PutMapping("updateArticle")
    @PreAuthorize("hasAuthority('article:update')")
    public Result updateArticle(@RequestBody Article article, HttpServletRequest req) {
        return articleService.updateArticle(article, req);
    }

    @DeleteMapping("deleteArticle")
    @PreAuthorize("hasAuthority('article:delete')")
    public Result deleteArticle(@RequestParam Integer id, HttpServletRequest req) {
        return articleService.deleteArticle(id, req);
    }


    @GetMapping("getAuditData")
    @PreAuthorize("hasAuthority('article:get_audit_data')")
    public Result getAuditData() {
        return articleService.getAuditData();
    }

    @PutMapping("passOrReject")
    @PreAuthorize("hasAuthority('article:pass_or_reject')")
    public Result passOrReject(@RequestBody PassAndReject passAndReject) {
        return articleService.passOrReject(passAndReject.getValue(), passAndReject.getArticleId());
    }

    @GetMapping("publicArticle")
    public Result publicArticle(@RequestParam(defaultValue = "1") Integer page) {
        return articleService.publicArticle(page);
    }
}
