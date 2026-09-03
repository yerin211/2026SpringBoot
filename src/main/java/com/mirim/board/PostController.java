package com.mirim.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/posts") //기본 주소값 만들기
public class PostController {

    private SmsNotifier notifier = new SmsNotifier();

    @GetMapping
    public String getPosts(@RequestParam(required = false) String keyword) {
        if(keyword != null) {
            return keyword + "(으)로 검색한 결과입니다.";
        }
        return "게시글의 목록입니다.";
    }

    @GetMapping("/count")
    public String getPostCount() {
        return "게시글 개수 : 0개";
    }

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable Long id){
        // 게시글 번호가 10번보다 크면 게시글이 없는거임
        if(id > 10 ){
            //404
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        }else if(id <= 0 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재할 수 없는 게시물입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(id + "번 게시글 입니다.");
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Map<String, Object> request) {
        String title = (String)request.get("title");
        String content = (String) request.get("content");

        //db에다가 데이터를 저장한다고 치고
        Map<String, Object> response = new HashMap<>();
        response.put("title",title);
        response.put("content",content);
        response.put("message","게시글이 등록되었습니다.");
        //이메일 발송
        notifier.send(title + " 게시글이 등록되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
        //return "["+title+"] 게시글이 등록되었습니다. 내용 : "+ content;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Map<String,Object> request){
        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재할 수 없는 게시글입니다.");
        }else if (id > 10){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        }
        String title = (String) request.get("title");
        String content = (String) request.get("content");

        Map<String, Object> response = new HashMap<>();
        response.put("id",id);
        response.put("title", title);
        response.put("content",content);
        response.put("message","게시글이 수정되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,@RequestBody Map<String, Object> request){
        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재할 수 없는 게시글입니다.");
        }else if(id > 10){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시글입니다.");
        }
        //db에 저장한다 치고
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
