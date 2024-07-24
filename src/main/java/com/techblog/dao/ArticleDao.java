package com.techblog.dao;

import com.techblog.model.Article;

import java.util.List;

public interface ArticleDao {
    void insertArticle(Article article);

    List<Article> selectUserArticles(String username);

    List<Article> selectAllArticles();

    Article getArticle(int id);
    void updateArticle(Article article);
    void deleteArticle(int id);

}
