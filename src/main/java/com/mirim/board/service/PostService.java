package com.mirim.board.service;

import com.mirim.board.Member;
import com.mirim.board.Notifier;
import com.mirim.board.Post;
import com.mirim.board.dto.LoginRequest;
import com.mirim.board.dto.MemberResponse;
import com.mirim.board.dto.PostResponse;
import com.mirim.board.exception.ApiException;
import com.mirim.board.repository.MemberRepository;
import com.mirim.board.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//PostRepository에게 종속됨 있어야만 얘도 존재 가능
public class PostService {

    private final PostRepository postRepository;
    private final Notifier notifier;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public PostService(PostRepository postRepository, Notifier notifier, MemberRepository memberRepository, PasswordEncoder passwordEncoder){
        this.postRepository = postRepository;
        this.notifier = notifier;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PostResponse getPost(Long id){
        //postRepository.existsById(id) == false
        Post post = postRepository.findById(id).orElse(null); //없으면 null 있으면 ㅔㅐㄴㅅ
        if(post == null){
            throw new ApiException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.");
        }
        return PostResponse.from(post);
//        if(!postRepository.existsById(id)){
//            return null;
//        }
//        Map<String, Object> post = new HashMap<>();
//        post.put("id",id);
//        post.put("title","게시글 제목");
//        post.put("content","게시글 내용");
//        return post;
    }
    public PostResponse createPost(String title, String content){
        //db에다가 데이터를 저장한다고 치고
        Post post = new Post(title, content);
        postRepository.save(post);

        //이메일 발송
        notifier.send(title + " 게시글이 등록되었습니다.");

        return PostResponse.from(post);

    }
    public PostResponse updatePost(Long id,String title,String content){

        Post post = postRepository.findById(id).orElse(null);

        if( post == null ){
            throw new ApiException(HttpStatus.NOT_FOUND,"존재하지 않는 게시글입니다.");
        }

        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
        return PostResponse.from(post);

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

    public List<PostResponse> getAllPosts(){
        return toResponses( postRepository.findAll());
    }
    public long getPostCount(){
        return postRepository.count();
    }

    public List<PostResponse> searchPosts(String keyword){
        return toResponses(postRepository.findByTitleContaining(keyword));
    }

    private List<PostResponse> toResponses(List<Post> posts){
        List<PostResponse> responses = new ArrayList<>();
        for(Post post : posts){
            responses.add(PostResponse.from(post));
        }
        return responses;
    }
    public MemberResponse login(LoginRequest request){
        Member member = memberRepository.findByLoginId(request.getLoginId()).orElse(null);
        if(member == null || !passwordEncoder.matches(request.getPassword(),member.getPassword()) ){ //평문, 암호문
            throw new ApiException(HttpStatus.UNAUTHORIZED,"아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        return MemberResponse.from(member);
    }
}
