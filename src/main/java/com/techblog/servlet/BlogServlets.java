package com.techblog.servlet;

import com.techblog.dao.ArticleDaoImpl;
import com.techblog.model.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BlogServlets", value = "/user/blog")
public class BlogServlets extends HttpServlet {
    private ArticleDaoImpl articleDao;

    @Override
    public void init() {

        articleDao=new ArticleDaoImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        List<Article> articles = articleDao.selectUserArticles(username);
        request.setAttribute("articles", articles);
        request.getRequestDispatcher("/article.jsp").forward(request, response);
    }


}