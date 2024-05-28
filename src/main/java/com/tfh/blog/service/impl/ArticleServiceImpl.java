package com.tfh.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tfh.blog.mapper.UserMapper;
import com.tfh.blog.pojo.Article;
import com.tfh.blog.pojo.vo.ModerateArticles;
import com.tfh.blog.pojo.vo.UserArticle;
import com.tfh.blog.service.ArticleService;
import com.tfh.blog.mapper.ArticleMapper;
import com.tfh.blog.utils.jwt.Token;
import com.tfh.blog.utils.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author B
 * @description 针对表【article】的数据库操作Service实现
 * @createDate 2024-05-23 17:05:15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Token token;

    @Override
    public Result getArticle(HttpServletRequest req, int current) {
        //获取用户id
        Long userId = token.getTokenById(req);
        //包装数据发送给前端
        ArrayList<Object> data = new ArrayList<>();

        //个人文章数,如果还有别的数量可以在这里继续添加
        List<Integer> articleList = articleMapper.variableByUserId(userId);
        data.add(articleList);

        //当前用户文章数据以及页数
        LambdaQueryWrapper<Article> eq = new LambdaQueryWrapper<Article>().eq(Article::getUserId, userId).orderByDesc(Article::getCreationTime);
        Page<Article> articlePage = articleMapper.selectPage(new Page<>(current, 12), eq);

        List<UserArticle> userArticles = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  |  HH:mm:ss ");
        articlePage.getRecords().forEach(a -> {
            UserArticle userArticle = new UserArticle();
            userArticle.setId(a.getId());
            userArticle.setCreationTime(simpleDateFormat.format(a.getCreationTime()));
            userArticle.setChangeTime(simpleDateFormat.format(a.getChangeTime()));
            userArticle.setStatus(a.getStatus());
            userArticle.setTitle(a.getTitle());
            userArticle.setContent(a.getContent());
            userArticles.add(userArticle);
        });

        long pages = articlePage.getPages();
        data.add(pages);
        data.add(userArticles);

        return Result.ok(data);
    }

    @Override
    public Result createArticle(Article article, HttpServletRequest req) {
        Long tokenById = token.getTokenById(req);

        article.setUserId(tokenById);
        article.setStatus(2);
        article.setId(null);
        article.setCreationTime(new Date(System.currentTimeMillis()));
        article.setChangeTime(new Date(System.currentTimeMillis()));
        article.setCount(1);
        articleMapper.insert(article);

        return Result.ok();
    }

    @Override
    public Result updateArticle(Article article, HttpServletRequest req) {
        Long tokenById = token.getTokenById(req);

        LambdaQueryWrapper<Article> eq = new LambdaQueryWrapper<Article>().eq(Article::getId, article.getId()).eq(Article::getUserId, tokenById);
        Article verify = articleMapper.selectOne(eq);
        if (verify != null) {
            verify.setContent(article.getContent());
            verify.setTitle(article.getTitle());
            verify.setStatus(2);
            verify.setChangeTime(new Date(System.currentTimeMillis()));
            verify.setCount(verify.getCount() + 1);
            articleMapper.updateById(verify);
        }

        return Result.ok();
    }

    @Override
    public Result deleteArticle(Integer id, HttpServletRequest req) {
        Long tokenById = token.getTokenById(req);
        LambdaQueryWrapper<Article> eq = new LambdaQueryWrapper<Article>().eq(Article::getId, id).eq(Article::getUserId, tokenById);
        Article article = articleMapper.selectOne(eq);

        if (article != null) {
            articleMapper.deleteById(article);
        }

        return Result.ok();
    }

    @Override
    public Result getAuditData() {
        List<Object> objects = new ArrayList<>();

        //获取未审核数
        Integer notReviewed = articleMapper.getHasNotBeenReviewed();
        objects.add(notReviewed != null ? notReviewed : 0);
        //审核失败
        Integer failure = articleMapper.auditFailure();
        objects.add(failure != null ? failure : 0);
        //审核通过
        Integer success = articleMapper.examinationPassed();
        objects.add(success != null ? success : 0);

        //获取审核文章
        LambdaQueryWrapper<Article> eq = new LambdaQueryWrapper<Article>().eq(Article::getStatus, 2);
        List<Article> articles = articleMapper.selectList(eq);
        List<ModerateArticles> moderateArticles = new ArrayList<>();
        objects.add(moderateArticles);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  |  HH:mm:ss ");
        articles.forEach(a -> {
            ModerateArticles moderateArticles1 = new ModerateArticles();
            moderateArticles1.setUserId(a.getUserId());
            moderateArticles1.setUserName(userMapper.selectById(a.getUserId()).getUserName());
            moderateArticles1.setArticleId(a.getId());
            moderateArticles1.setCreationTime(simpleDateFormat.format(a.getCreationTime()));
            moderateArticles1.setCount(a.getCount());
            moderateArticles1.setTitle(a.getTitle());
            moderateArticles1.setContent(a.getContent());
            moderateArticles.add(moderateArticles1);
        });

        return Result.ok(objects);
    }

    @Override
    public Result passOrReject(Integer value, Integer articleId) {

        LambdaUpdateWrapper<Article> eq = new LambdaUpdateWrapper<Article>()
                .set(value == 1, Article::getStatus, 1)
                .set(value > 2, Article::getStatus, 3)
                .eq(Article::getId, articleId);
        articleMapper.update(eq);

        return Result.ok();
    }


    @Override
    public Result publicArticle(Integer page) {
        ArrayList<Object> objects = new ArrayList<>();

        LambdaQueryWrapper<Article> eq = new LambdaQueryWrapper<Article>()
                .orderByDesc(Article::getId)
                .eq(Article::getStatus, 1)
                .select(Article::getId, Article::getTitle, Article::getContent);
        Page<Article> articlePage = articleMapper.selectPage(new Page<>(page, 10), eq);
        List<Article> records = articlePage.getRecords();
        long pages = articlePage.getPages();
        objects.add(records);
        objects.add(pages);
        return Result.ok(objects);
    }

}




