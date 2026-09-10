package com.mirim.board;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BoardApplication {

    public static void main(String[] args) {
        //1. 필요한 객체들을 찾아서 만들어둡니다.
        //2. 만들어서 창고에 저장한다. (창고 = 스프링 컨테이너)
        //3. 내장 톰켓(서버)를 띄운다. 그에 맞는 포트(8080)가 열린다.
        //4. 요청을 받아서 요처이 오면 알맞는 코드로 넘겨준다.
        ApplicationContext context = SpringApplication.run(BoardApplication.class, args);

        Notifier notifier1= context.getBean(Notifier.class);
        Notifier notifier2= context.getBean(Notifier.class);
        System.out.println("같은 객체인가? "+ (notifier1== notifier2));

        Notifier direct1= new EmailNotifier();
        Notifier direct2= new EmailNotifier();
        System.out.println("직접 만들면 같은 객체인가? "+(direct1== direct2));

        try {
            context.getBean(SmsNotifier.class);
        }catch(NoSuchBeanDefinitionException e){
            System.out.println("SmsNotifier는 컨테이너에 존재하지 않음 : "+e.getMessage());
        }

        System.out.println("등록된 Bean 개수 : "+ context.getBeanDefinitionNames().length);
        System.out.println("emailNotifier 등록 여부 : "+context.containsBean("emailNotifier"));
        System.out.println("SmsNotifier 등록 여부 : "+context.containsBean("SmsNotifier"));

    }
}
