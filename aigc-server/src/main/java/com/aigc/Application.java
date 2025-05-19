package com.aigc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：jiang
 * @date ：2024/4/26 16:40
 * @description ：引导类程序
 */
@SpringBootApplication
@MapperScan("com.aigc.mapper")
public class  Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
