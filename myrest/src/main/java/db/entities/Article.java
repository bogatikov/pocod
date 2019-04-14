package db.entities;

import com.google.gson.Gson;

import javax.persistence.*;


@Entity
@Table(name = "articles")
public class Article {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "title")
    private String title;

    @Column(name = "text", length = 1000)
    private String text;

    @Column(name = "published_at")
    private String published_at;

    public Article() {
    }

    public Article(String title, String text, String published_at) {
        this.title = title;
        this.text = text;
        this.published_at = published_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public Gson toJson() {
        Gson gson = new Gson();
        gson.toJson(title);
        gson.toJson(text);
        gson.toJson(published_at);
        return gson;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", published_at='" + published_at + '\'' +
                '}';
    }
}
