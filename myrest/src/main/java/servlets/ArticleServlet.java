package servlets;

import base.DBServiceInterface;
import com.google.gson.*;
import db.DBException;
import db.entities.Article;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ArticleServlet extends HttpServlet {

    public static final String PATH = "/article";

    private final DBServiceInterface dbService;

    public ArticleServlet(DBServiceInterface dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        resp.setContentType("application/json;charset=utf-8");
        if (id != null) {
            if (id.equals("all")) {
                try {
                    List list = dbService.getAllArticles();
                    Gson gson = new Gson();
                    resp.getWriter().println(gson.toJson(list));
                } catch (DBException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    e.printStackTrace();
                }

            } else {
                Long art_id = Long.parseLong(id);
                try {
                    Article article = dbService.getArticleById(art_id);
                    Gson gson = new Gson();
                    resp.getWriter().println(gson.toJson(article));
                } catch (DBException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    e.printStackTrace();
                }

            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder jsonBuff = new StringBuilder();
        String line;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jsonBuff.append(line);
        } catch (Exception e) { /*error*/ }

        resp.setContentType("application/json;charset=utf-8");

        Gson gson = new Gson();
        Article article = gson.fromJson(jsonBuff.toString(), Article.class);
        article.setPublished_at(new Date().toString());
        try {
            Long id = dbService.addArticle(article);
            resp.getWriter().println(gson.toJson(id));
        } catch (DBException e) {
            e.printStackTrace();
            resp.getWriter().println("error");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        StringBuilder jsonBuff = new StringBuilder();
        String line;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jsonBuff.append(line);
        } catch (Exception e) { /*error*/ }

        Gson gson = new Gson();
        Article article = gson.fromJson(jsonBuff.toString(), Article.class);

        try {
            dbService.remove(article);
        } catch (DBException e) {
            resp.getWriter().println(gson.toJson("error"));
        }
    }
}
