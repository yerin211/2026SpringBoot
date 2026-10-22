package com.mirim.board;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 500)
    private String content;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;
    @Column(name="created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    public Comment(Long id, String content, Post post, Member author, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.post = post;
        this.author = author;
        this.createdAt = createdAt;
    }

    public Comment() {

    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Post getPost() {
        return post;
    }

    public Member getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
