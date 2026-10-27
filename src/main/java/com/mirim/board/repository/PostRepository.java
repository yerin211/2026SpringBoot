package com.mirim.board.repository;

import com.mirim.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    //제네릭으로  Post, Long을 사용 <entity, entity가 어떤 타입인지>
    //7개의 메서드들이 모두 CRUD의 속성이 들어갔는데 그것들은 어디에서든 쓰여서  JpaRepository로 상속 받을 수 있음

    List<Post> findByTitleContaining(String keyword);
    //url에 들어가는 제목으로 검색하기 와 같은 것들은 CRUD에 무조건 필요한거 아니기 때문에
    //Spring Boot에서 지원하는 키워드들을 메소드 이름으로 쓰면 Spring Boot가 그걸 읽어서 알아서 쿼리를 만들어줌
    //findBy = select * from posts where title= ''
    //title = 조건
    //selct * fron posts where title like '%%'
    //List<Post> findByContentContaining(String keyword);
    //서버 시작 전에 미리 잘 됬는지 작동해봄

    @Query("select p from Post p join fetch p.author")
    List<Post> findAllWithAuthor();

}
