package db;

import base.DBServiceInterface;
import db.dao.ArticleDAO;
import db.entities.Article;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBService implements DBServiceInterface {

    private final SessionFactory sessionFactory;

    public DBService() {
        this.sessionFactory = createSessionFactory();
    }

    public Long addArticle(Article article) throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            ArticleDAO articleDAO = new ArticleDAO(session);
            long id = articleDAO.insertArticle(article);
            transaction.commit();
            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public Article getArticleById(Long id) throws DBException {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            ArticleDAO articleDAO = new ArticleDAO(session);
            Article article = articleDAO.get(id);
            transaction.commit();
            return article;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List getAllArticles() throws DBException{
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            ArticleDAO articleDAO = new ArticleDAO(session);
            List list = articleDAO.getAllArticles();
            transaction.commit();
            return list;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void remove(Article article) throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            ArticleDAO articleDAO = new ArticleDAO(session);
            articleDAO.removeArticle(article);
            transaction.commit();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    private static SessionFactory createSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
