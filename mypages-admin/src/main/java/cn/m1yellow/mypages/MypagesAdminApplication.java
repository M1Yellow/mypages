package cn.m1yellow.mypages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.m1yellow.mypages"}) // 默认扫描是启动类 MypagesAdminApplication 所在的同级目录及子目录
public class MypagesAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypagesAdminApplication.class, args);
    }

}
