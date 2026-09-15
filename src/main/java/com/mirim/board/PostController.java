package com.mirim.board;

import aQute.bnd.annotation.licenses.MPL_2_0;
import com.mirim.board.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts") //기본 주소값 만들기
public class PostController {

    private final PostService postService;
//    private final Notifier notifier;  //결합도가 높은 이유
    //UP캐스팅 바뀌지 않도록

    public PostController(Notifier notifier, PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getPosts(@RequestParam(required = false) String keyword) {
        if(keyword != null) {
            List<Map<String, Object>> posts = postService.searchPosts(keyword);
            return ResponseEntity.ok(posts);
        }
        List<Map<String,Object>> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/count")
    public String getPostCount() {
        long postCount = postService.getPostCount();
        return "게시글 개수 : "+postCount + "개";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id){
        // 게시글 번호가 10번보다 크면 게시글이 없는거임
//        if(id > 10 ){
//            //404
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");}
        if(id <= 0 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재할 수 없는 게시물입니다.");
        }
//        return ResponseEntity.status(HttpStatus.OK).body(id + "번 게시글 입니다.");
        //Map<String, Object> post = postService.getPost(id);
        Map<String, Object> post = postService.getPost(id);
        if(post == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시물입니다.");
        }
        return  ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Map<String, Object> request) {
        String title = (String)request.get("title");
        String content = (String) request.get("content");

        Map<String, Object> response = postService.createPost(title,content);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        //return "["+title+"] 게시글이 등록되었습니다. 내용 : "+ content;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Map<String,Object> request){


        String title = (String) request.get("title");
        String content = (String) request.get("content");

        Map<String, Object> response = postService.createPost(title,content);
        if (response == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,@RequestBody Map<String, Object> request){
        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재할 수 없는 게시글입니다.");
        }if(id > 10) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        }

        boolean deleted = postService.deletePost(id);
        if(!deleted){
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).build();
        }

        //db에 저장한다 치고
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
