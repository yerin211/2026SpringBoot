package com.mirim.board;

import jakarta.persistence.*;

@Entity //테이블이랑 연결된 객체라는 뜻
@Table(name = "posts") //어떤 테이블이랑 연결할 건지 알려주는
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Post(String content, String title) {   //우리를 위해서
        this.content = content;
        this.title = title;
    }

    public Post() { //JPA를 위해서
    }
}
