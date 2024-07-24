package com.techblog.controller;

import com.techblog.dao.ArticleDaoImpl;
import com.techblog.model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ArticleController", value = "/user/blog/actions")
public class ArticleController extends HttpServlet {
    private ArticleDaoImpl articleDao;

    @Override
    public void init() {
        articleDao = new ArticleDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            request.getRequestDispatcher("/article-form.jsp").forward(request, response);
        } else {
            try {
                switch (action) {
                    case "new":
                        request.getRequestDispatcher("/article-form.jsp").forward(request, response);
                        break;
                    case "insert":
                        insertArticle(request, response);
                        break;
                    case "userArticle":
                        myArticles(request, response);
                        break;
                    case "allArticles":
                        allArticles(request, response);
                        break;
                    case "edit":
                        showEditArticle(request, response);
                        break;
                    case "update":
                        updateArticle(request, response);
                        break;
                    case "delete":
                        deleteArticle(request, response);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void insertArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = (String) request.getSession().getAttribute("username");
        String title = request.getParameter("title");
        String text = request.getParameter("article");

        Article article = new Article();
        article.setTitle(title);
        article.setArticle(text);
        article.setUsername(username);

        articleDao.insertArticle(article);
        response.sendRedirect(request.getContextPath() + "/user/blog");
    }

    private void myArticles(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = (String) request.getSession().getAttribute("username");
        List<Article> articles = articleDao.selectUserArticles(username);
        request.setAttribute("articles", articles);
        request.getRequestDispatcher("/article.jsp").forward(request, response);
    }

    private void allArticles(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Article> articles = articleDao.selectAllArticles();
        request.setAttribute("articles", articles);
        request.getRequestDispatcher("/article.jsp").forward(request, response);
    }

    private void showEditArticle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Article article = articleDao.getArticle(id);
        request.setAttribute("article", article);
        request.getRequestDispatcher("/article-form.jsp").forward(request, response);
    }

    private void updateArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String article1 = request.getParameter("article");

        Article article = articleDao.getArticle(id);
        article.setTitle(title);
        article.setArticle(article1);

        articleDao.updateArticle(article);
        response.sendRedirect(request.getContextPath() + "/user/blog");
    }

    private void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        articleDao.deleteArticle(id);
        response.sendRedirect(request.getContextPath() + "/user/blog");
    }

}
