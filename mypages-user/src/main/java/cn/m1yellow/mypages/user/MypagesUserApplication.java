package cn.m1yellow.mypages.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.m1yellow.mypages"})
public class MypagesUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypagesUserApplication.class, args);
    }

}
