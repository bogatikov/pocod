package db.dao;

import db.entities.Article;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ArticleDAO {
    private Session session;

    public ArticleDAO(Session session) {
        this.session = session;
    }

    public Article get(Long id) {
        CriteriaBuilder cb = session.getCriteriaBuilder(); // Создаем билдер
        CriteriaQuery<Article> cq = cb.createQuery(Article.class); // Создаём условия запроса
        Root articles = cq.from(Article.class); // Указываем откуда идёт выборка

        /*
        Выбираем из таблицы, которая связана с User.class, где
         колонка id равна параметру id
         */
        cq.where(cb.equal(articles.get("id"), cb.parameter(Long.class, "id")));

        // Из условий запроса получаем запрос
        Query query = session.createQuery(cq);
        // Устанавливаем параметр
        query.setParameter("id", id);


        return (Article) query.getSingleResult();
    }

    public List getAllArticles() {
        CriteriaBuilder cb = session.getCriteriaBuilder(); // Создаем билдер
        CriteriaQuery<Article> cq = cb.createQuery(Article.class); // Создаём условия запроса
        Root articles = cq.from(Article.class); // Указываем откуда идёт выборка
        // Из условий запроса получаем запрос
        Query query = session.createQuery(cq);

        return query.getResultList();
    }

    public long insertArticle(Article article) throws HibernateException {
        return (Long) session.save(article);
    }

    public void removeArticle(Article article) {
        session.remove(article);
    }
}
