package com.mirim.board.dto;

import com.mirim.board.Post;

import java.time.LocalDateTime;

public class PostResponse {

    private final Long id;
    private final String title;
    private final AuthorResponse author;
    private final String content;
    private final LocalDateTime createdAt;

    public PostResponse(Long id, String title, AuthorResponse author, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static PostResponse from(Post post){
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                AuthorResponse.from(post.getAuthor()),
                post.getContent(),
                post.getCreatedAt()
        );
    }

    //get을 해야지 json으로 나옴
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public AuthorResponse getAuthor(){return author;}
}
