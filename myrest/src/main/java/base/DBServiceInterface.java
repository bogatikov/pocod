package base;

import db.DBException;
import db.entities.Article;

import java.util.List;

public interface DBServiceInterface {
    Long addArticle(Article article) throws DBException;
    Article getArticleById(Long id) throws DBException;
    List getAllArticles() throws DBException;
    void remove(Article article) throws DBException;
}
