package com.mirim.board;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity //테이블이랑 연결된 객체라는 뜻
@Table(name = "posts") //어떤 테이블이랑 연결할 건지 알려주는
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 200)
    private String title;
    private String content;
    //다른 옵션이 붙거나 이름이 다를 경우에 @Column으로 어노테이션을 붙여준다.
    @Column(name = "created_at", nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @Column(name= "view_count",nullable = false)
    private Long viewCount;

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
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0L;
    }

    public Post() { //JPA를 위해서
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public Long getViewCount(){
        return viewCount;
    }

}
