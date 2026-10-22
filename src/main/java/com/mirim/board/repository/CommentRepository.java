package com.mirim.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //게시글 아이디로 댓글 목록 만들기
    List<Comment> findByPostId(Long postId);
}
