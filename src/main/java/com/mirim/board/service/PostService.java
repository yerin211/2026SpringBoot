package com.mirim.board.service;

import com.mirim.board.Notifier;
import com.mirim.board.Post;
import com.mirim.board.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//PostRepository에게 종속됨 있어야만 얘도 존재 가능
public class PostService {

    private final PostRepository postRepository;
    private final Notifier notifier;

    public PostService(PostRepository postRepository, Notifier notifier){
        this.postRepository = postRepository;
        this.notifier = notifier;
    }

    public Post getPost(Long id){
        //postRepository.existsById(id) == false
        return postRepository.findById(id).orElse(null); //없으면 null 있으면 ㅔㅐㄴㅅ
//        if(!postRepository.existsById(id)){
//            return null;
//        }
//        Map<String, Object> post = new HashMap<>();
//        post.put("id",id);
//        post.put("title","게시글 제목");
//        post.put("content","게시글 내용");
//        return post;
    }
    public Post createPost(String title, String content){
        //db에다가 데이터를 저장한다고 치고
        Post post = new Post(title, content);
        postRepository.save(post);

        //이메일 발송
        notifier.send(title + " 게시글이 등록되었습니다.");

        return post;

    }
    public Post updatePost(Long id,String title,String content){

        Post post = postRepository.findById(id).orElse(null);

        if( post == null ){
            return null;
        }

        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
        return post;

    }


    public boolean deletePost(Long id) {
        Post post = postRepository.findById(id).orElse(null);

        if(post == null){
            return false;
        }

        postRepository.deleteById(id);
        return true;
//        if (!postRepository.existsById((id))) {
//            return false;
//        }
//        return true;
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    public long getPostCount(){
        return postRepository.count();
    }
    public List<Post> searchPosts(String keyword){
        return postRepository.findByTitleContaining(keyword);
    }
}
