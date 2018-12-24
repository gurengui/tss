package com.baizhi.entity;

public class Poetries {
    private Integer id;
    private String title;
    private String content;

    private Poets poets;

    @Override
    public String
    toString() {
        return "Poetries{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", poets=" + poets +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Poets getPoets() {
        return poets;
    }

    public void setPoets(Poets poets) {
        this.poets = poets;
    }

    public Poetries(Integer id, String title, String content, Poets poets) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.poets = poets;
    }

    public Poetries() {
    }
}
