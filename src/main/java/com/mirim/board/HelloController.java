package com.mirim.board;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//Controller 는 문자가 아니라 View(화면 이름)로 인식한다(화면을 옮기려고 한다.)
//RestController 는 화면 이름이 아니라 그 자체로 인기
@RestController
public class HelloController {

    //브라우저 -> 내장 톰캣(서버) -> 교통 정리 담당 -> HelloController.hello()
    //교통정리 담당 = DispatcherServlet
    //컨트롤러로 보냄 ex.HelloController
    //DispatcherServlet이 하는 일
    // - 주소를 보고 어느 메서드로 보낼지 고른다. ex./hello
    // - 목적지가 없다면 404를 응답한다.

    //CRUD : Create / *Read(Get)* / Update / Delete
    //브라우저에서 주소창으로 직접 요청할때는 GET 이외의 메서드는 보낼 수 없다.
    //1. 게시글 작성하는 어떻게 테스트할까?
    //2. RestController, GetMapping 뭐하는 애들일까?

    @Value("${my.message}")
    private String message;

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello")//주소 창에 /hello 추가
    public String hello2(){
       return message;
//        throw new RuntimeException("일부러 에러를 냈습니다.");
    }
    @GetMapping("/hello-map")
    public Map<String, Object> helloMap(){
        return Map.of("name","김미림","grade",2);
    }
}
